package com.example.demo.management.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.management.entity.Dialogue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.management.entity.request.AddDialogRequest;
import com.example.demo.management.entity.request.AddMessageRequest;
import com.example.demo.management.entity.request.PageRequest;
import com.example.demo.management.entity.response.SelectDialogResponse;
import com.example.demo.management.entity.response.SelectMessageResponse;

/**
 * <p>
 * 会话表 服务类
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
public interface DialogueService extends IService<Dialogue> {
    /**
     * 增加消息
     * @param addDialogRequest 增加对话实体类
     */
    void addDialog(AddDialogRequest addDialogRequest);

    /**
     * 逻辑删除表示已读,一次全部表示已读
     * @param loginId
     */
    void delDialog(String loginId);

    /**
     * 逻辑删除表示已读,表示一个消息已读
     * @param dialogueId
     */
    void delDialogById(String dialogueId);

    /**
     * 获取消息列表
     * @param loginId
     * @param pageRequest
     * @return
     */
    IPage<SelectDialogResponse> selectDialog(String loginId, PageRequest pageRequest);

    /**
     * 计算未读的消息
     * @param loginId
     */
    int countUnreadDialog(String loginId);
}
