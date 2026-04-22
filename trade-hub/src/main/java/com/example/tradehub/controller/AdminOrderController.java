package com.example.tradehub.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.tradehub.entity.dto.OrderQueryDTO;
import com.example.tradehub.entity.pojo.Order;
import com.example.tradehub.result.PageResult;
import com.example.tradehub.result.Result;
import com.example.tradehub.service.AdminOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单管理模块")
@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;
    @GetMapping("/page")
    @Operation(summary = "分页查询订单列表")
    public Result<PageResult<Order>> pageQuery(OrderQueryDTO query){
        return Result.success(adminOrderService.pageQuery(query));
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询单个订单详情")
    public Result<Order> getById(@PathVariable Long id){
        Order order = adminOrderService.getById(id);
        return Result.success(order);
    }

    @PutMapping("/status/{id}")
    @Operation(summary = "修改订单状态")
    public Result updateStatus(@PathVariable Long id, @RequestParam Integer status){
        adminOrderService.updateStatus(id, status);
        return Result.success();
    }

    @PutMapping("/cancel/{id}")
    @Operation(summary = "强制取消订单")
    public Result cancelOrder(@PathVariable Long id){
        adminOrderService.updateStatus(id, 4);
        return Result.success();
    }
}
