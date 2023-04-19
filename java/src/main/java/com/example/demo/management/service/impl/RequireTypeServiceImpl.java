package com.example.demo.management.service.impl;

import com.example.demo.management.entity.RequireType;
import com.example.demo.management.mapper.RequireTypeMapper;
import com.example.demo.management.service.RequireTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 需求类型表 服务实现类
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
@Service
public class RequireTypeServiceImpl extends ServiceImpl<RequireTypeMapper, RequireType> implements RequireTypeService {

    @Resource
    RequireTypeMapper requireTypeMapper;

    @Override
    public String getRequireType(String typeId) {
        System.out.println(typeId);
        String name = requireTypeMapper.selectType(typeId);
        return name;
    }
}
