package com.example.demo.management.service;

import com.example.demo.management.entity.RequireType;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 需求类型表 服务类
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
public interface RequireTypeService extends IService<RequireType> {
    String getRequireType(String typeId);
}
