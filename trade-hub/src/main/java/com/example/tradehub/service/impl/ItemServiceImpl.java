package com.example.tradehub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tradehub.constant.MessageConstant;
import com.example.tradehub.entity.pojo.Item;
import com.example.tradehub.entity.pojo.User;
import com.example.tradehub.exception.ItemNoFoundException;
import com.example.tradehub.mapper.ItemMapper;
import com.example.tradehub.result.PageResult;
import com.example.tradehub.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService  {

    private final ItemMapper itemMapper;
    @Override
    public void onShelf(Long id) {
        //1.查询商品是否存在
        Item item = this.getById(id);
        if (item==null){
            throw new ItemNoFoundException(MessageConstant.ITEM_NOT_FOUND);
        }

        //2.检查当前状态
        Integer currentStatus = item.getStatus();
        if (currentStatus==null){
            throw new RuntimeException("商品状态异常");
        }
        if (currentStatus==1){
            throw new RuntimeException("商品已上架，无需重复操作");
        }
        if (currentStatus==2){
            throw new RuntimeException("商品已下架，无法上架");
        }
        if (currentStatus==3){
            throw new RuntimeException("商品已售出，无法上架");
        }

        // 3. 更新状态为已上架
        LambdaUpdateWrapper<Item> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Item::getId, id);
        updateWrapper.set(Item::getStatus, 1);
        this.update(updateWrapper);
        log.info("商品上架成功，商品ID: {}", id);
    }

    @Override
    public void offShelf(Long id) {
        // 1. 查询商品是否存在
        Item item = this.getById(id);
        if (item==null){
            throw new ItemNoFoundException(MessageConstant.ITEM_NOT_FOUND);
        }
        // 2. 检查当前状态
        if (item.getStatus()!=1){
            throw new RuntimeException("只有已上架的商品才能下架");
        }

        // 3. 更新状态为已下架
        LambdaUpdateWrapper<Item> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Item::getId, id);
        updateWrapper.set(Item::getStatus, 2);
        this.update(updateWrapper);
        log.info("商品下架成功，商品ID: {}", id);
    }

    @Override
    public PageResult<Item> pageQuery(int page, int pageSize, String title, Long userId, Integer status , Long categoryId) {
        Page<Item> itemPage = new Page<>(page , pageSize);
        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper<>();
        //判断title非空才拼接
        if (StringUtils.hasText(title)){
            queryWrapper.like(Item::getTitle, title);
        }
        if (userId !=null){
            queryWrapper.eq(Item::getUserId, userId);
        }
        if (status !=null){
            queryWrapper.eq(Item::getStatus, status);
        }
        if (categoryId !=null){
            queryWrapper.eq(Item::getCategoryId, categoryId);
        }
        queryWrapper.orderByDesc(Item::getCreateTime);

        Page<Item> resultPage = itemMapper.selectPage(itemPage, queryWrapper);

        return new PageResult<>(resultPage.getTotal(), resultPage.getRecords());
    }
}
