package com.example.demo.management.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.management.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.management.entity.request.AddMessageRequest;
import com.example.demo.management.entity.request.PageRequest;
import com.example.demo.management.entity.response.SelectMessageResponse;

/**
 * <p>
 * 系统消息表 服务类
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
public interface MessageService extends IService<Message> {
    /**
     * 增加消息
     * @param addMessageRequest 增加消息实体类
     */
    void addMessage(AddMessageRequest addMessageRequest);

    /**
     * 逻辑删除表示已读,一次全部表示已读
     * @param loginId
     */
    void delMessage(String loginId);

    /**
     * 逻辑删除表示已读,表示一个消息已读
     * @param messageId
     */
    void delMessageById(String messageId);

    /**
     * 获取消息列表
     * @param loginId
     * @param pageRequest
     * @return
     */
    IPage<SelectMessageResponse> selectMessage(String loginId, PageRequest pageRequest);

    /**
     * 计算未读的消息
     * @param loginId
     */
    int countUnreadMessage(String loginId);
}
