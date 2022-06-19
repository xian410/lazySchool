package com.example.demo.management.mapper;

import com.example.demo.management.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 账户信息表 Mapper 接口
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
public interface AccountMapper extends BaseMapper<Account> {

    Float getProfile(@Param("requireId") String requireId);
}
