package com.example.demo.management.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.exception.CustomException;
import com.example.demo.management.entity.Dialogue;
import com.example.demo.management.entity.Message;
import com.example.demo.management.entity.constact.Prefix;
import com.example.demo.management.entity.request.AddDialogRequest;
import com.example.demo.management.entity.request.PageRequest;
import com.example.demo.management.entity.response.SelectDialogResponse;
import com.example.demo.management.entity.response.SelectMessageResponse;
import com.example.demo.management.mapper.DialogueMapper;
import com.example.demo.management.mapper.InformationMapper;
import com.example.demo.management.service.DialogueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 会话表 服务实现类
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
@Service
public class DialogueServiceImpl extends ServiceImpl<DialogueMapper, Dialogue> implements DialogueService {

    @Resource
    DialogueMapper dialogueMapper;

    @Resource
    InformationMapper informationMapper;

    @Override
    public void addDialog(AddDialogRequest addDialogRequest) {
        Dialogue dialogue = new Dialogue();
        BeanUtils.copyProperties(addDialogRequest,dialogue);
        dialogue.setDialogueId(Prefix.DIALOG_PREFIX + UUID.randomUUID());
        dialogueMapper.insert(dialogue);
    }

    @Override
    public void delDialog(String loginId) {
        List<Dialogue> list = dialogueMapper.selectList(Wrappers.lambdaQuery(Dialogue.class)
                .eq(Dialogue::getDialogueId,loginId));

        list.forEach(dialogue -> {
            dialogue.setIsDelete(true);
            dialogueMapper.updateById(dialogue);
        });
    }

    @Override
    public void delDialogById(String dialogueId) {
        Dialogue dialogue = dialogueMapper.selectOne(Wrappers.lambdaQuery(Dialogue.class)
                .eq(Dialogue::getDialogueId,dialogueId));
        if (dialogue == null) {
            throw new CustomException("消息不存在");
        }

        dialogue.setIsDelete(true);
        dialogueMapper.updateById(dialogue);
    }

    @Override
    public IPage<SelectDialogResponse> selectDialog(String loginId, PageRequest pageRequest) {
        IPage<SelectDialogResponse> queryPageRequest = new Page<>();
        queryPageRequest.setCurrent(pageRequest.getCurrent());
        queryPageRequest.setSize(pageRequest.getSize());

        IPage<SelectDialogResponse> page = dialogueMapper.selectDialogs(queryPageRequest, loginId);

        return page;
    }

    @Override
    public int countUnreadDialog(String loginId) {
        int count = dialogueMapper.selectCount(Wrappers.lambdaQuery(Dialogue.class)
                .eq(Dialogue::getLoginId,loginId)
                .eq(Dialogue::getIsDelete,false));
        return count;
    }
}
