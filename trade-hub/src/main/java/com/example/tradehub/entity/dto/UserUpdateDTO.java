package com.example.tradehub.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateDTO {
    private Long id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    private String password;

    private String avatar;  // 头像

    private Integer status; // 状态（只有管理员可以修改）
}
