package com.example.demo.management.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.management.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.management.entity.response.SelectMessageResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统消息表 Mapper 接口
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
public interface MessageMapper extends BaseMapper<Message> {
    /**
     * 查看消息
     */
    IPage<SelectMessageResponse> selectMessages(IPage<SelectMessageResponse> queryPageRequest,
                                                @Param("loginId") String loginId);
}
