package com.example.tradehub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tradehub.entity.dto.OrderQueryDTO;
import com.example.tradehub.entity.pojo.Order;
import com.example.tradehub.mapper.AdminOrderMapper;
import com.example.tradehub.result.PageResult;
import com.example.tradehub.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl extends ServiceImpl<AdminOrderMapper, Order> implements AdminOrderService {

    private final AdminOrderMapper orderMapper;

    @Override
    public PageResult<Order> pageQuery(OrderQueryDTO query) {
        Page<Order> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();

        // 动态查询条件
        if (StringUtils.hasText(query.getOrderNo())) {
            wrapper.like(Order::getOrderNo, query.getOrderNo());
        }
        if (query.getStatus() != null) {
            wrapper.eq(Order::getStatus, query.getStatus());
        }
        if (query.getBuyerId() != null) {
            wrapper.eq(Order::getBuyerId, query.getBuyerId());
        }
        if (query.getSellerId() != null) {
            wrapper.eq(Order::getSellerId, query.getSellerId());
        }

        wrapper.orderByDesc(Order::getCreateTime);
        Page<Order> result = orderMapper.selectPage(page, wrapper);
        return new PageResult<>(result.getTotal(), result.getRecords());
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        // 1. 校验订单是否存在
        Order order = this.getById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        // 2. 校验状态值合法性 (0:待支付, 1:已支付, 2:已发货, 3:已完成, 4:已取消)
        if (status < 0 || status > 4) {
            throw new RuntimeException("订单状态不合法");
        }

        // 3. 执行更新
        LambdaUpdateWrapper<Order> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Order::getId, id);
        updateWrapper.set(Order::getStatus, status);
        this.update(updateWrapper);
    }

    // 生成订单编号：时间戳 + UUID前8位
    /*private String generateOrderNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        return "ORD" + timestamp + uuid;
    }*/
}
