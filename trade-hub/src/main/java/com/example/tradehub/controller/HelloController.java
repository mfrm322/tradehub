package com.example.tradehub.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "测试模块")
@RestController
public class HelloController {
    @Operation(summary = "测试接口")
    @GetMapping("/hi")
    public String hello(){

        return "你好，世界";
    }
}
