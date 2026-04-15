package com.example.tradehub.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloController {
    @GetMapping("/hi")
    public String hello(){

        return "你好，勾八郭";
    }
}
