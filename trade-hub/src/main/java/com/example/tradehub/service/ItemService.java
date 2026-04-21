package com.example.tradehub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.tradehub.entity.pojo.Item;
import com.example.tradehub.result.PageResult;

public interface ItemService extends IService<Item> {
    void onShelf(Long id);

    void offShelf(Long id);

    PageResult<Item> pageQuery(int page, int pageSize, String title, Long userId, Integer status ,Long categoryId);
}
