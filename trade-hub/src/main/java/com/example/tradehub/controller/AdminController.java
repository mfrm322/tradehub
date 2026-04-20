package com.example.tradehub.controller;


import com.example.tradehub.entity.dto.AdminLoginDTO;
import com.example.tradehub.entity.dto.AdminRegisterDTO;
import com.example.tradehub.entity.dto.BatchDeleteDTO;
import com.example.tradehub.entity.dto.UserUpdateDTO;
import com.example.tradehub.entity.pojo.User;
import com.example.tradehub.entity.vo.AdminLoginVO;
import com.example.tradehub.entity.vo.AdminRegisterVO;
import com.example.tradehub.result.PageResult;
import com.example.tradehub.result.Result;
import com.example.tradehub.service.AdminService;
import com.example.tradehub.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理员模块")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "管理员登录")
    @PostMapping("/login")
    public Result<AdminLoginVO> login(@RequestBody @Validated AdminLoginDTO adminLoginDTO){

        User user = adminService.login(adminLoginDTO);

        //登录成功，生成jwt
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        AdminLoginVO adminLoginVO = new AdminLoginVO();
        adminLoginVO.setId(user.getId());
        adminLoginVO.setUsername(user.getUsername());
        adminLoginVO.setToken(token);
        return Result.success(adminLoginVO);
    }

    @Operation(summary = "管理员注册")
    @PostMapping("/register")
    public Result<AdminRegisterVO> register(@RequestBody @Validated AdminRegisterDTO adminRegisterDTO){
        User user = adminService.register(adminRegisterDTO);

        AdminRegisterVO adminRegisterVO = new AdminRegisterVO();
        adminRegisterVO.setId(user.getId());
        adminRegisterVO.setUsername(user.getUsername());

        return Result.success(adminRegisterVO);
    }

    @Operation(summary = "根据id查询用户")
    @GetMapping("/id")
    public Result<User> getById(@RequestParam Long id){
        return Result.success(adminService.getById(id));
    }

    @Operation(summary = "分页查询用户")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(
            @RequestParam(defaultValue = "1") int page ,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String username){

        return Result.success(adminService.pageQuery(page, pageSize, username));
    }

    @Operation(summary = "根据id删除")
    @DeleteMapping("/id")
    public Result deleteById(@RequestParam Long id){
         adminService.removeById(id);
         return Result.success();
    }

    @Operation(summary = "批量删除用户")
    @DeleteMapping("/batch")
    public  Result deleteBatch(@RequestBody BatchDeleteDTO batchDeleteDTO){
        adminService.removeBatchByIds(batchDeleteDTO.getIds());
        return Result.success();
    }

    @Operation(summary = "修改用户信息")
    @PutMapping("/update")
    public Result updateUser(@RequestBody UserUpdateDTO userUpdateDTO){
        adminService.updateUser(userUpdateDTO);
        return Result.success();
    }

}
