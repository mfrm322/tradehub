package com.example.tradehub.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("orders")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String orderNo;       // 订单编号
    private Long buyerId;        // 买家ID
    private Long sellerId;       // 卖家ID
    private Long itemId;         // 商品ID
    private Double amount;       // 订单金额
    private Integer status;      // 订单状态 0:待支付 1:已支付 2:已发货 3:已完成 4:已取消
    private String address;      // 收货地址

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

