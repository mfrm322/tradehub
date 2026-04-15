package com.example.tradehub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tradehub.constant.MessageConstant;
import com.example.tradehub.constant.StatusConstant;
import com.example.tradehub.entity.dto.AdminLoginDTO;
import com.example.tradehub.entity.pojo.User;

import com.example.tradehub.exception.AccountLockedException;
import com.example.tradehub.exception.AccountNotFoundException;
import com.example.tradehub.exception.PasswordErrorException;
import com.example.tradehub.mapper.AdminMapper;
import com.example.tradehub.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper, User> implements AdminService {

    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //登录的具体业务
    @Override
    public User login(AdminLoginDTO adminLoginDTO) {
        String username = adminLoginDTO.getUsername();
        String password = adminLoginDTO.getPassword();

        log.info("登录请求 - 用户名: {}, 密码: {}", username, password);

        //1、根据用户名查询数据库中的数据
        //User user= adminMapper.getByUsername(username);
        User user = adminMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));

        //2.处理各种异常（用户名不存在、密码不对、账号被锁定）
        //账号不存在
        if (user==null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        log.info("数据库中的密码: {}", user.getPassword());
        log.info("密码长度: {}", user.getPassword().length());

        boolean matches = passwordEncoder.matches(password, user.getPassword());
        log.info("密码验证结果: {}", matches);

        if (!matches) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //账号被锁定
        if (StatusConstant.DISABLE.equals(user.getStatus())) {
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //返回这个这个用户对象
        return user;
    }
}
