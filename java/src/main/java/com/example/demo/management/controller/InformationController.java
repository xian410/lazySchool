package com.example.demo.management.controller;


import com.example.demo.common.ApiResponse;
import com.example.demo.common.annotation.AccessToken;
import com.example.demo.common.annotation.IgnoreAccessToken;
import com.example.demo.management.entity.LoginUser;
import com.example.demo.management.entity.request.SetInfoRequest;
import com.example.demo.management.entity.response.InfoResponse;
import com.example.demo.management.service.InformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */

@Api(tags = "用户个人信息相关")
@CrossOrigin
@RestController
@RequestMapping("/management/information")
public class InformationController {

    @Resource
    InformationService informationService;

    @AccessToken
    @ApiOperation("用户修改个人信息")
    @PostMapping("/modCustomerInfo")
    public ApiResponse<Object> modCustomerInfo(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @RequestBody SetInfoRequest setInfoRequest
    ) {
        informationService.modInfo(setInfoRequest,loginUser.getLoginId());
        return ApiResponse.success();
    }

    @AccessToken
    @ApiOperation("获取用户个人信息")
    @PostMapping("/getCustomerInfo")
    public ApiResponse<InfoResponse> getCustomerInfo(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser
    ) {
        InfoResponse infoResponse = informationService.getInfo(loginUser.getLoginId());
        return ApiResponse.success(infoResponse);
    }

    @ApiOperation("用户修改个人信息(id)")
    @PostMapping("/modCustomerInfoById")
    public ApiResponse<Object> modCustomerInfo(
             @RequestBody SetInfoRequest setInfoRequest
    ) {
        informationService.modInfo(setInfoRequest,setInfoRequest.getLoginId());
        return ApiResponse.success();
    }

    @ApiOperation("获取用户个人信息(id)")
    @PostMapping("/getCustomerInfoById")
    public ApiResponse<InfoResponse> getCustomerInfo(
            @RequestBody Map<String, Object> mp
    ) {
        String loginId = (String) mp.get("loginId");
        InfoResponse infoResponse = informationService.getInfo(loginId);
        return ApiResponse.success(infoResponse);
    }

    @AccessToken
    @ApiOperation("上传头像")
    @PostMapping("/uploadHeadPic")
    public ApiResponse<String> headPic(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @RequestPart MultipartFile file
    ) {
        String name;
        try {
            name = informationService.uploadHeadPic(file, loginUser);
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage());
        }
        return ApiResponse.success(0, "success", name);
    }
}

