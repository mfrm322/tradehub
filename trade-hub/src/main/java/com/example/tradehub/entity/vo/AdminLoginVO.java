package com.example.tradehub.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginVO {

    //用户id
    private Long id;

    //用户名
    private String username;

    //jwt令牌
    private String token;
}
