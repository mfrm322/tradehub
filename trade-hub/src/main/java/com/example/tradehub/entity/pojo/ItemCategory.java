package com.example.tradehub.entity.pojo;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("categories")
public class ItemCategory {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;       //分类名称
    private Integer sort;     //排序值（越小越靠前）
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
