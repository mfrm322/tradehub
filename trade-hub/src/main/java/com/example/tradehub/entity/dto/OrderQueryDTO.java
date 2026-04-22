package com.example.tradehub.entity.dto;

import lombok.Data;

@Data
public class OrderQueryDTO {
    private String orderNo;
    private Integer status;
    private Long buyerId;
    private Long sellerId;

    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
