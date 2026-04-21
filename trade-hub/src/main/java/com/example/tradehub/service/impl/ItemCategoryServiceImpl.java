package com.example.tradehub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tradehub.entity.dto.ItemCategoryDTO;
import com.example.tradehub.entity.pojo.Item;
import com.example.tradehub.entity.pojo.ItemCategory;
import com.example.tradehub.exception.BaseException;
import com.example.tradehub.mapper.ItemCategoryMapper;
import com.example.tradehub.mapper.ItemMapper;
import com.example.tradehub.result.PageResult;
import com.example.tradehub.service.ItemCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ItemCategoryServiceImpl extends ServiceImpl<ItemCategoryMapper, ItemCategory> implements ItemCategoryService {
    private final ItemCategoryMapper itemCategoryMapper;
    private final ItemMapper itemMapper;
    @Override
    public PageResult<ItemCategory> pageQuery(int page, int pageSize, String name) {
        Page<ItemCategory> categoryPage = new Page<>(page , pageSize);
        LambdaQueryWrapper<ItemCategory> queryWrapper = new LambdaQueryWrapper<>();
        //1. 按名称模糊搜索
        if (StringUtils.hasText(name)){
            queryWrapper.like(ItemCategory::getName, name);
        }

        // 2. 按 sort 升序，再按 create_time 倒序
        queryWrapper.orderByAsc(ItemCategory::getSort);
        queryWrapper.orderByDesc(ItemCategory::getCreateTime);
        Page<ItemCategory> resultPage = itemCategoryMapper.selectPage(categoryPage,queryWrapper);

        return new PageResult<>(resultPage.getTotal() , resultPage.getRecords());
    }

    @Override
    public void addCategory(ItemCategoryDTO itemCategoryDTO) {
        ItemCategory category = new ItemCategory();
        category.setName(itemCategoryDTO.getName());
        category.setSort(itemCategoryDTO.getSort());
        this.save(category);
    }

    @Override
    public void updateCategory(ItemCategoryDTO itemCategoryDTO) {
        // 1. 检查分类是否存在
        ItemCategory exist = this.getById(itemCategoryDTO.getId());
        if (exist == null) {
            throw new BaseException("分类不存在");
        }

        // 2. 构建更新条件
        LambdaUpdateWrapper<ItemCategory> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ItemCategory::getId, itemCategoryDTO.getId());

        // 3. 只更新非空字段（选择性更新）
        if (StringUtils.hasText(itemCategoryDTO.getName())) {
            // 可选：检查新名称是否与其他分类冲突
            updateWrapper.set(ItemCategory::getName, itemCategoryDTO.getName());
        }
        if (itemCategoryDTO.getSort() != null) {
            updateWrapper.set(ItemCategory::getSort, itemCategoryDTO.getSort());
        }

        this.update(updateWrapper);
    }

    @Override
    public void deleteCategoryById(Long id) {
        //1. 检查该分类下是否有关联的商品
        Long count = itemMapper.selectCount(new LambdaQueryWrapper<Item>()
                .eq(Item::getCategoryId, id));

        if (count>0){
            throw new BaseException("该分类下有商品，请先删除商品");
        }
        this.removeById(id);
    }
}
