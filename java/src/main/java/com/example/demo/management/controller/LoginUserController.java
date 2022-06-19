package com.example.demo.management.controller;


import com.example.demo.common.ApiResponse;
import com.example.demo.common.annotation.AccessToken;
import com.example.demo.common.exception.CustomException;
import com.example.demo.management.entity.LoginUser;
import com.example.demo.management.entity.request.ChangePasswordRequest;
import com.example.demo.management.entity.request.LoginRequest;
import com.example.demo.management.entity.request.RegisterRequest;
import com.example.demo.management.entity.response.LoginInformationResponse;
import com.example.demo.management.service.LoginUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * <p>
 * 用户登录表 前端控制器
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
@Api(tags = "登录注册相关")
@CrossOrigin
@RestController
@RequestMapping("/management/login-user")
public class LoginUserController {
    @Autowired
    LoginUserService loginUserService;

    @PostMapping("/register")
    @ApiOperation("注册账号")
    public ApiResponse register(
            @RequestBody  RegisterRequest registerRequest) {
        LoginInformationResponse loginInformationResponse = loginUserService.Register(registerRequest);
        return ApiResponse.success(loginInformationResponse);
    }


    @PostMapping("/delUser")
    @ApiOperation(value = "删除账户")
    public ApiResponse<Object> delCount(
            @ApiParam(name = "loginId") @RequestBody Map<String, Object> mp
    ) {
        String loingId = mp.get("loginId").toString();
        try {
            loginUserService.delUser(loingId);
        } catch (CustomException e) {
            return ApiResponse.error(e.getMsg());
        }
        return ApiResponse.success();
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public ApiResponse<LoginInformationResponse> login(
            @Validated @RequestBody LoginRequest loginRequest
    ) {
        LoginInformationResponse loginInformationResponse = loginUserService.login(loginRequest);
        return ApiResponse.success(loginInformationResponse);
    }

    @PostMapping("/adminLogin")
    @ApiOperation(value = "管理员登录")
    public ApiResponse<LoginInformationResponse> adminLogin(
            @Validated @RequestBody LoginRequest loginRequest
    ) {
        LoginInformationResponse loginInformationResponse = loginUserService.adminLogin(loginRequest);
        return ApiResponse.success(loginInformationResponse);
    }



    @AccessToken
    @PostMapping("/logout")
    @ApiOperation(value = "退出登录")
    public ApiResponse<LoginInformationResponse> logout(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser
    ) {
        loginUserService.logout(loginUser.getLoginId());
        return ApiResponse.success();
    }

    @AccessToken
    @PostMapping("/getUserType")
    @ApiOperation(value = "获取用户类型")
    public ApiResponse<Integer> getUserType(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser
    ) {
        Integer userType = loginUserService.getUserType(loginUser.getLoginId());
        return ApiResponse.success(userType);
    }

    @ApiOperation("修改密码")
    @AccessToken
    @PostMapping("/changePassword")
    public ApiResponse<Object> changePassword(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @Validated @RequestBody ChangePasswordRequest changePasswordRequest
    ) {
        loginUserService.changePassword(changePasswordRequest,loginUser.getLoginId());
        return ApiResponse.success();
    }
}

