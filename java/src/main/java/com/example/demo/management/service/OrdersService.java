package com.example.demo.management.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.management.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.management.entity.request.AddOrderRequest;
import com.example.demo.management.entity.request.SelectOrdersRequest;
import com.example.demo.management.entity.response.SelectOrdersResponse;

/**
 * <p>
 * 订单信息表 服务类
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
public interface OrdersService extends IService<Orders> {
    /**
     * 添加订单
     * @param
     */
    void addOrders(AddOrderRequest addOrderRequest, String loginId);

    /**
     * 修改订单
     * @param
     */
    void modifyOrders(AddOrderRequest addOrderRequest, String loginId);

    /**
     * 删除订单
     * @param orderId
     */
    void deleteOrders(String orderId);

    /**
     * 取消订单
     * @param orderId
     */
    void cancelOrders(String orderId);

    /**
     * 配送人员查看的订单
     */
    IPage<SelectOrdersResponse> selectDriverOrders(SelectOrdersRequest selectOrdersRequest, String loginId);

    /**
     * 顾客查看自己的订单
     */
    IPage<SelectOrdersResponse> selectCustomerOrders(SelectOrdersRequest selectOrdersRequest, String loginId);

    /**
     * 管理员查看全部订单
     * @param selectOrdersRequest
     * @return
     */
    IPage<SelectOrdersResponse> selectAllOrders(SelectOrdersRequest selectOrdersRequest);
}
