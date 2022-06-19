package com.example.demo.management.mapper;

import com.example.demo.management.entity.Information;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
public interface InformationMapper extends BaseMapper<Information> {
    /**
     * 获取名字
     * @param loginId
     * @return
     */
    String getName(@Param("loginId") String loginId);
}
