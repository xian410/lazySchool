package com.example.demo.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.exception.CustomException;
import com.example.demo.management.entity.Account;
import com.example.demo.management.entity.Information;
import com.example.demo.management.entity.LoginUser;
import com.example.demo.management.entity.constact.Common;
import com.example.demo.management.entity.constact.Prefix;
import com.example.demo.management.entity.request.ChangePasswordRequest;
import com.example.demo.management.entity.request.LoginRequest;
import com.example.demo.management.entity.request.RegisterRequest;
import com.example.demo.management.entity.response.LoginInformationResponse;
import com.example.demo.management.mapper.AccountMapper;
import com.example.demo.management.mapper.InformationMapper;
import com.example.demo.management.mapper.LoginUserMapper;
import com.example.demo.management.service.LoginUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Driver;
import java.util.UUID;

/**
 * <p>
 * 用户登录表 服务实现类
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
@Service
public class LoginUserServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser> implements LoginUserService {

    @Resource
    LoginUserMapper loginUserMapper;

    @Resource
    InformationMapper informationMapper;

    @Resource
    AccountMapper accountMapper;

    @Override
    public LoginInformationResponse Register(RegisterRequest registerRequest) {
        int count = loginUserMapper.selectCount(
                Wrappers.lambdaQuery(LoginUser.class)
                        .eq(LoginUser::getLoginId,registerRequest.getLoginId())
        );
        if (count > 0) {
            throw new CustomException(1, "该账号已存在");
        }
        if (!registerRequest.getPassword().equals(registerRequest.getPassword2())) {
            throw new CustomException(1, "两次输入的密码不一致");
        }
        if (!registerRequest.getCode().equals("111")) {
            throw new CustomException(1, "请输入正确验证码!");
        }
        if (registerRequest.getPassword().length() < Common.MAXLENGTH) {
            throw new CustomException(1, "密码长度不得少于6位");
        }

        LoginUser loginUser = new LoginUser();
        Information information = new Information();
        Account account = new Account();

        //赋值
        loginUser.setLoginId(registerRequest.getLoginId());
        loginUser.setPassword(registerRequest.getPassword());
        loginUser.setUserType(registerRequest.getType());

        information.setLoginId(registerRequest.getLoginId());
        information.setTelephone(registerRequest.getLoginId());
        information.setHeadPicUrl("/file/default.jpg");
        //information.setName(registerRequest.getName());

        account.setLoginId(registerRequest.getLoginId());
        /**
         * getType == 0: 买家
         * getType == 1: 买家+配送人员 双身份
         */
        loginUserMapper.insert(loginUser);
        informationMapper.insert(information);
        accountMapper.insert(account);

        loginUser.setToken(Prefix.TOKEN_PREFIX + UUID.randomUUID());
        loginUserMapper.updateById  (loginUser);
        LoginInformationResponse loginInformationResponse = new LoginInformationResponse();
        BeanUtils.copyProperties(loginUser, loginInformationResponse);
        return loginInformationResponse;


    }


    @Override
    public LoginInformationResponse login(LoginRequest loginRequest) {

        LoginUser loginUser = loginUserMapper.selectOne(Wrappers.lambdaQuery(LoginUser.class)
                .eq(LoginUser::getLoginId,loginRequest.getLoginId()));
        if (loginUser == null) {
            throw new CustomException("不存在此用户");
        }
        if (!loginUser.getPassword().equals(loginRequest.getPassword())) {
            throw new CustomException("密码错误");
        }

        loginUser.setToken(Prefix.TOKEN_PREFIX + UUID.randomUUID());
        loginUserMapper.updateById  (loginUser);
        LoginInformationResponse loginInformationResponse = new LoginInformationResponse();
        BeanUtils.copyProperties(loginUser, loginInformationResponse);
        return loginInformationResponse;
    }

    @Override
    public LoginInformationResponse adminLogin(LoginRequest loginRequest) {
        LoginUser loginUser = loginUserMapper.selectOne(Wrappers.lambdaQuery(LoginUser.class)
                .eq(LoginUser::getLoginId,loginRequest.getLoginId()));
        if (loginUser == null) {
            throw new CustomException("不存在此用户");
        }
        if (!loginUser.getPassword().equals(loginRequest.getPassword())) {
            throw new CustomException("密码错误");
        }
        if(!loginUser.getUserType().equals(Common.ADMIN)) {
            throw new CustomException("权限等级不足");
        }

        loginUser.setToken(Prefix.TOKEN_PREFIX + UUID.randomUUID());
        loginUserMapper.updateById  (loginUser);
        LoginInformationResponse loginInformationResponse = new LoginInformationResponse();
        BeanUtils.copyProperties(loginUser, loginInformationResponse);
        return loginInformationResponse;
    }

    @Override
    public void SendCode(RegisterRequest registerRequest) {

    }

    @Override
    public void logout(String userId) {
        // 将token赋空字符串
        loginUserMapper.update(null,
                Wrappers.lambdaUpdate(LoginUser.class)
                        .set(LoginUser::getToken,"")
                        .eq(LoginUser::getLoginId,userId));
    }

    @Override
    public int getUserType(String loginId) {
        LoginUser loginUser = loginUserMapper.selectOne(Wrappers.lambdaQuery(LoginUser.class)
                .eq(LoginUser::getLoginId,loginId));
        if (loginUser == null) {
            throw new CustomException("不存在此用户");
        }
        return loginUser.getUserType();
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest, String loginId) {
        LoginUser loginUser = loginUserMapper.selectOne(
                Wrappers.lambdaQuery(LoginUser.class)
                        .eq(LoginUser::getLoginId, loginId)
        );
        if (!changePasswordRequest.getPassword().equals(loginUser.getPassword())) {
            throw new CustomException(1, "原密码错误！");
        } else if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getNewPasswordAgain())) {
            throw new CustomException(1, "两次输入密码不一致！");
        } else if (changePasswordRequest.getNewPassword().length() < Common.MAXLENGTH) {
            throw new CustomException(1, "密码长度不得少于6位");
        } else {
            loginUser.setPassword(changePasswordRequest.getNewPassword());
            loginUserMapper.updateById(loginUser);
        }
    }

    @Override
    public void delUser(String loingId) {
        int row = loginUserMapper.delete(new UpdateWrapper<LoginUser>().eq("login_id", loingId));
        if (row == 0) {
            throw new CustomException("删除失败");
        }
    }
}
