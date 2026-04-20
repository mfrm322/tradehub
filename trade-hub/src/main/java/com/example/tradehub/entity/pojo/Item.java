package com.example.tradehub.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("items")
public class Item {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;              //商品标题
    private Double price;             //商品价格
    private String description;      //商品描述
    private String image;           //商品图片
    private Long userId;           //商品所属用户
    private Long categoryId;      //商品所属分类
    private Integer status;      //商品状态 0:待审核 1:已上架 2:已下架 3:已售出
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
