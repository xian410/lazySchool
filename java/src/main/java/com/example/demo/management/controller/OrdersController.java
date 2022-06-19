package com.example.demo.management.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.common.ApiResponse;
import com.example.demo.common.annotation.AccessToken;
import com.example.demo.management.entity.LoginUser;
import com.example.demo.management.entity.request.AddOrderRequest;
import com.example.demo.management.entity.request.SelectOrdersRequest;
import com.example.demo.management.entity.response.SelectOrdersResponse;
import com.example.demo.management.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * <p>
 * 订单信息表 前端控制器
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
@AccessToken
@Api(tags = "订单相关")
@CrossOrigin
@RestController
@RequestMapping("/management/orders")
public class OrdersController {
    @Resource
    OrdersService ordersService;

    @ApiOperation("添加订单")
    @PostMapping("/addOrders")
    public ApiResponse<Object> addOrders(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @Validated @RequestBody AddOrderRequest addOrderRequest
    ) {
        ordersService.addOrders(addOrderRequest,loginUser.getLoginId());
        return ApiResponse.success();
    }

    @PostMapping("/delOrders")
    @ApiOperation("删除订单")
    public ApiResponse<Object> delOrders(
            @RequestBody String orderId
    ) {
        ordersService.deleteOrders(orderId);
        return ApiResponse.success();
    }

    @PostMapping("/cancelOrders")
    @ApiOperation("取消订单")
    public ApiResponse<Object> cancelOrders(
            @RequestBody String orderId
    ) {
        ordersService.cancelOrders(orderId);
        return ApiResponse.success();
    }

    @PostMapping("/modifyOrders")
    @ApiOperation("修改订单")
    public ApiResponse<Object> modifyOrders(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @Validated @RequestBody AddOrderRequest addOrderRequest
    ) {
        ordersService.modifyOrders(addOrderRequest,loginUser.getLoginId());
        return ApiResponse.success();
    }

    @PostMapping("/selectDriverOrders")
    @ApiOperation("司机查看自己的订单")
    public ApiResponse<IPage<SelectOrdersResponse>> selectDriverOrders(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @RequestBody SelectOrdersRequest selectOrdersRequest
    ) {
        IPage<SelectOrdersResponse> page = ordersService.selectDriverOrders(selectOrdersRequest, loginUser.getLoginId());
        return ApiResponse.success(page);
    }

    @PostMapping("/selectCustomerOrders")
    @ApiOperation("货主查看自己的订单")
    public ApiResponse<IPage<SelectOrdersResponse>> selectCustomerOrders(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @RequestBody SelectOrdersRequest selectOrdersRequest
    ) {
        IPage<SelectOrdersResponse> page = ordersService.selectCustomerOrders(selectOrdersRequest, loginUser.getLoginId());
        return ApiResponse.success(page);
    }

    @PostMapping("/selectAllOrders")
    @ApiOperation("查看全部订单")
    public ApiResponse<IPage<SelectOrdersResponse>> selectAllOrders(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @RequestBody SelectOrdersRequest selectOrdersRequest
    ) {
        IPage<SelectOrdersResponse> page = ordersService.selectAllOrders(selectOrdersRequest);
        return ApiResponse.success(page);
    }
}

