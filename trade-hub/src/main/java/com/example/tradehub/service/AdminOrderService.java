package com.example.tradehub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.tradehub.entity.dto.OrderQueryDTO;
import com.example.tradehub.entity.pojo.Order;
import com.example.tradehub.result.PageResult;

public interface AdminOrderService extends IService<Order> {
    PageResult<Order> pageQuery(OrderQueryDTO query);

    void updateStatus(Long id, Integer status);
}
