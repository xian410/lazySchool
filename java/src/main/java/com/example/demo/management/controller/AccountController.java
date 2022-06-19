package com.example.demo.management.controller;


import com.example.demo.common.ApiResponse;
import com.example.demo.common.annotation.AccessToken;
import com.example.demo.common.exception.CustomException;
import com.example.demo.management.entity.LoginUser;
import com.example.demo.management.entity.request.AddMoneyRequest;
import com.example.demo.management.entity.request.ModifyAccountRequest;
import com.example.demo.management.entity.request.ReleaseRequireRequest;
import com.example.demo.management.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * <p>
 * 账户信息表 前端控制器
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
@Api(tags = "账户相关")
@CrossOrigin
@AccessToken
@RestController
@RequestMapping("/management/account")
public class AccountController {

    @Resource
    AccountService accountService;

    @PostMapping("/addMoney")
    @ApiOperation(value = "充值金额")
    public ApiResponse<Float> addMoney(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @RequestBody AddMoneyRequest addMoneyRequest
    ) {
        Float money = accountService.addMoney(loginUser.getLoginId(),addMoneyRequest.getMoney());
        return ApiResponse.success(money);
    }

    @PostMapping("/modifyAccount")
    @ApiOperation("转账")
    public ApiResponse<Object> modifyAccount(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @Validated @RequestBody ModifyAccountRequest modifyAccountRequest
    ) {
        try {
            accountService.modifyAccount(modifyAccountRequest);
        } catch (CustomException e) {
            return ApiResponse.error(e.getMsg());
        }
        return ApiResponse.success();
    }

    @PostMapping("/profile")
    @ApiOperation("统计配送人员赚的钱")
    public ApiResponse<Object> profile(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser
    ) {
        Float profile = accountService.profile(loginUser);
        return ApiResponse.success(profile);
    }

    @PostMapping("/ordersCount")
    @ApiOperation("统计配送人员的接单量")
    public ApiResponse<Object> ordersCount(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser
    ) {
        Integer count = accountService.ordersCount(loginUser);
        return ApiResponse.success(count);
    }
    
}

