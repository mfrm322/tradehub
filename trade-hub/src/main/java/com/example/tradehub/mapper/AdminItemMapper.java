package com.example.tradehub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tradehub.entity.pojo.Item;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminItemMapper extends BaseMapper<Item> {
}
