package com.example.tradehub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tradehub.entity.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminOrderMapper extends BaseMapper<Order> {
}
