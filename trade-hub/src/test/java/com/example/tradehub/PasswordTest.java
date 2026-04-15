package com.example.tradehub;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String dbPassword = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";
        String inputPassword = "123456";

        System.out.println("数据库密码: " + dbPassword);
        System.out.println("输入密码: " + inputPassword);
        System.out.println("验证结果: " + encoder.matches(inputPassword, dbPassword));

        // 重新生成一个
        String newPassword = encoder.encode("123456");
        System.out.println("\n新生成的密码: " + newPassword);
        System.out.println("新密码验证: " + encoder.matches("123456", newPassword));
    }
}
