package com.example.tradehub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.tradehub.entity.dto.ItemCategoryDTO;
import com.example.tradehub.entity.pojo.ItemCategory;
import com.example.tradehub.result.PageResult;

public interface AdminItemCategoryService extends IService<ItemCategory> {
    PageResult<ItemCategory> pageQuery(int page, int pageSize, String name);

    void addCategory(ItemCategoryDTO itemCategoryDTO);

    void updateCategory(ItemCategoryDTO itemCategoryDTO);

    void deleteCategoryById(Long id);
}
