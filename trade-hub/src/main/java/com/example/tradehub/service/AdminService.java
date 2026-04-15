package com.example.tradehub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.tradehub.entity.dto.AdminLoginDTO;
import com.example.tradehub.entity.pojo.User;
import com.example.tradehub.entity.vo.AdminLoginVO;
import com.example.tradehub.result.Result;

public interface AdminService extends IService<User> {
    User login(AdminLoginDTO adminLoginDTO);
}
