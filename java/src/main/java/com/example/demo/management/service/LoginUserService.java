package com.example.demo.management.service;

import com.example.demo.management.entity.LoginUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.management.entity.request.ChangePasswordRequest;
import com.example.demo.management.entity.request.LoginRequest;
import com.example.demo.management.entity.request.RegisterRequest;
import com.example.demo.management.entity.response.LoginInformationResponse;

/**
 * <p>
 * 用户登录表 服务类
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
public interface LoginUserService extends IService<LoginUser> {
    /**
     * 用户
     * @param loginRequest
     * @return LoginInformationResponse 用户信息响应
     */
    LoginInformationResponse login(LoginRequest loginRequest);

    /**
     * 管理员登录
     * @param loginRequest
     * @return LoginInformationResponse 用户信息响应
     */
    LoginInformationResponse adminLogin(LoginRequest loginRequest);

    void SendCode(RegisterRequest registerRequest);

    /**
     * 注册
     * @param registerRequest
     */
    LoginInformationResponse Register(RegisterRequest registerRequest);

    /**
     * 退出登录
     * @param userId
     */
    void logout(String userId);

    /**
     * 获取用户类型
     * @param loginId
     * @return
     */
    int getUserType(String loginId);

    /**
     * 修改密码
     */
    void changePassword(ChangePasswordRequest changePasswordRequest, String loginId);

    /**
     * 删除用户
     * @param loingId
     */
    void delUser(String loingId);
}
