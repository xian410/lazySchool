package com.example.demo.management.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.exception.CustomException;
import com.example.demo.management.entity.Message;
import com.example.demo.management.entity.constact.Prefix;
import com.example.demo.management.entity.request.AddMessageRequest;
import com.example.demo.management.entity.request.PageRequest;
import com.example.demo.management.entity.response.SelectMessageResponse;
import com.example.demo.management.mapper.InformationMapper;
import com.example.demo.management.mapper.MessageMapper;
import com.example.demo.management.mapper.RequiresMapper;
import com.example.demo.management.service.LoginUserService;
import com.example.demo.management.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * <p>
 * 系统消息表 服务实现类
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
@Service
    public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Resource
    MessageMapper messageMapper;

    @Resource
    LoginUserService loginUserService;

    @Resource
    RequiresMapper requiresMapper;

    @Resource
    InformationMapper informationMapper;


    @Override
    public void addMessage(AddMessageRequest addMessageRequest) {
        Message message = new Message();
        BeanUtils.copyProperties(addMessageRequest, message);
        message.setMessageId(Prefix.MESSAGE_PREFIX + UUID.randomUUID());
        messageMapper.insert(message);
    }

    @Override
    public void delMessage(String loginId) {
        List<Message> list = messageMapper.selectList(Wrappers.lambdaQuery(Message.class)
                .eq(Message::getLoginId,loginId));

        list.forEach(message -> {
            message.setIsDelete(true);
            messageMapper.updateById(message);
        });
    }

    @Override
    public void delMessageById(String messageId) {
        String messageId1 = messageId.substring(1,messageId.length()-1);
        Message message = messageMapper.selectOne(Wrappers.lambdaQuery(Message.class)
                .eq(Message::getMessageId,messageId1));
        if (message == null) {
            throw new CustomException("消息不存在");
        }

        message.setIsDelete(true);
        messageMapper.updateById(message);
    }

    @Override
    public IPage<SelectMessageResponse> selectMessage(String loginId, PageRequest pageRequest) {
        IPage<SelectMessageResponse> queryPageRequest = new Page<>();
        queryPageRequest.setCurrent(pageRequest.getCurrent());
        queryPageRequest.setSize(pageRequest.getSize());

        IPage<SelectMessageResponse> page = messageMapper.selectMessages(queryPageRequest, loginId);

        List<SelectMessageResponse> list = page.getRecords();

        list.forEach(message -> {
//            String userId = message.getFromUserId();
//            String goodId = message.getGoodId();
//
//            if (!"".equals(userId)) {
//                //判断用户类型查姓名
//                if (loginUserService.getUserType(userId) == 0) {
//                    message.setFromUserName(informationMapper.getName(userId));
//                } else {
//                    message.setFromUserName(informationMapper.getName(userId));
//                }
//            }
//
//            if (!"".equals(goodId)) {
//                message.setGoodName(requiresMapper.getRequireName(goodId));
//            }

            switch (message.getStatus()) {
                case 1: {
                    message.setMessage("您的订单已有人接单");
                    break;
                }
                case 2: {
                    message.setMessage("您的订单 "+message.getGoodId().substring(32).toUpperCase()+" 已到达");
                    break;
                }
                case 3: {
                    message.setMessage("您的运费已到账");
                    break;
                }
                case 4: {
                    message.setMessage("您的订单 "+message.getGoodId().substring(32).toUpperCase()+" 已被取消");
                    break;
                }
                default:message.setMessage("");
            }
        });

        page.setRecords(list);
        System.out.println(page);
        return page;
    }

    @Override
    public int countUnreadMessage(String loginId) {
        int count = messageMapper.selectCount(Wrappers.lambdaQuery(Message.class)
                .eq(Message::getLoginId,loginId)
                .eq(Message::getIsDelete,false));
        return count;
    }
}
