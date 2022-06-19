package com.example.demo.management.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.management.entity.Dialogue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.management.entity.response.SelectDialogResponse;
import com.example.demo.management.entity.response.SelectMessageResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会话表 Mapper 接口
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
public interface DialogueMapper extends BaseMapper<Dialogue> {
    /**
     * 查看对话消息
     */
    IPage<SelectDialogResponse> selectDialogs(IPage<SelectDialogResponse> queryPageRequest,
                                               @Param("loginId") String loginId);
}
