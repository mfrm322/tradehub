package com.example.tradehub.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemCategoryDTO {
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;
    @NotNull(message = "排序值不能为空")
    private Integer sort;
}
