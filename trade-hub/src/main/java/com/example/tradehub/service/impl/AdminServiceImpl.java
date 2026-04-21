package com.example.tradehub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tradehub.constant.MessageConstant;
import com.example.tradehub.constant.StatusConstant;
import com.example.tradehub.entity.dto.AdminLoginDTO;
import com.example.tradehub.entity.dto.AdminRegisterDTO;
import com.example.tradehub.entity.dto.UserUpdateDTO;
import com.example.tradehub.entity.pojo.User;

import com.example.tradehub.exception.AccountLockedException;
import com.example.tradehub.exception.AccountNotFoundException;
import com.example.tradehub.exception.PasswordErrorException;
import com.example.tradehub.mapper.AdminMapper;
import com.example.tradehub.result.PageResult;
import com.example.tradehub.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


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



    //用户注册
    @Override
    public User register(AdminRegisterDTO adminRegisterDTO) {
        String username = adminRegisterDTO.getUsername();
        String password = adminRegisterDTO.getPassword();

        //1、查询当前用户名是否已经存在
        User existUser = adminMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
        //2、如果已经存在，抛出异常
        if (existUser != null) {
            throw new RuntimeException(MessageConstant.USER_EXIST);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setAvatar("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiEFJ_wTKQQIFuw3jKvZn0L_2YhqrcY596RQ&s");
        user.setRole("ADMIN");
        user.setStatus(StatusConstant.ENABLE);
        adminMapper.insert(user);
        return user;
    }

    //分页查询
    @Override
    public PageResult<User> pageQuery(int page, int pageSize, String username) {
        Page<User> userPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //判断username非空才拼接
        if (StringUtils.hasText(username)){
            queryWrapper.like(User::getUsername, username);
        }
        //添加排序条件
        queryWrapper.orderByDesc(User::getCreateTime);
        Page<User> resultPage = adminMapper.selectPage(userPage, queryWrapper);
        return new PageResult<>(resultPage.getTotal(), resultPage.getRecords());

    }

    @Override
    public void updateUser(UserUpdateDTO userUpdateDTO) {
        Long userId = userUpdateDTO.getId();

        //1.先查询用户是否存在
        User existUser = this.getById(userId);
        if (existUser == null) {
            throw new RuntimeException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, userId);
        updateWrapper.set(User::getUsername, userUpdateDTO.getUsername());
        updateWrapper.set(User::getPassword, passwordEncoder.encode(userUpdateDTO.getPassword()));
        updateWrapper.set(User::getAvatar, userUpdateDTO.getAvatar());
        updateWrapper.set(User::getStatus, userUpdateDTO.getStatus());

        this.update(updateWrapper);

    }
}
