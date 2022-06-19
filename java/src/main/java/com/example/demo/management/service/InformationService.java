package com.example.demo.management.service;

import com.example.demo.management.entity.Information;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.management.entity.LoginUser;
import com.example.demo.management.entity.request.SetInfoRequest;
import com.example.demo.management.entity.response.InfoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
public interface InformationService extends IService<Information> {
    /**
     * 修改个人信息
     * @param setInfoRequest
     * @param customerId
     */
    void modInfo(SetInfoRequest setInfoRequest, String customerId);


    InfoResponse getInfo(String customerId);

    /**
     * 上传头像
     *
     * @param file      头像文件
     * @param loginUser 用户
     * @return String 头像原文件名
     * @throws IOException 流异常
     * @author lishan
     * @since 2021-01-28
     */
    String uploadHeadPic(MultipartFile file, LoginUser loginUser) throws IOException;


}
