package com.example.demo.management.mapper;

import com.example.demo.management.entity.RequireType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 需求类型表 Mapper 接口
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
public interface RequireTypeMapper extends BaseMapper<RequireType> {
    String selectType(String typeId);
}
