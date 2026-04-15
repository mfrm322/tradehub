package com.example.tradehub.controller;


import com.example.tradehub.entity.dto.AdminLoginDTO;
import com.example.tradehub.entity.pojo.User;
import com.example.tradehub.entity.vo.AdminLoginVO;
import com.example.tradehub.result.Result;
import com.example.tradehub.service.AdminService;
import com.example.tradehub.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final JwtUtil jwtUtil;
    @PostMapping("/login")
    public Result<AdminLoginVO> login(@RequestBody @Validated AdminLoginDTO adminLoginDTO){

        User user = adminService.login(adminLoginDTO);

        //登录成功，生成jwt
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        AdminLoginVO adminLoginVO = new AdminLoginVO();
        adminLoginVO.setId(user.getId());
        adminLoginVO.setUsername(user.getUsername());
        adminLoginVO.setName(user.getNickname());
        adminLoginVO.setToken(token);
        return Result.success(adminLoginVO);
    }
}
