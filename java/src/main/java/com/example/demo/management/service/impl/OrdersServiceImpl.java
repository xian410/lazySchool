package com.example.demo.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.exception.CustomException;
import com.example.demo.management.entity.LoginUser;
import com.example.demo.management.entity.Orders;
import com.example.demo.management.entity.Requires;
import com.example.demo.management.entity.constact.Common;
import com.example.demo.management.entity.constact.Prefix;
import com.example.demo.management.entity.enums.GenderEnum;
import com.example.demo.management.entity.request.AddMessageRequest;
import com.example.demo.management.entity.request.AddOrderRequest;
import com.example.demo.management.entity.request.AddTradingRecordRequest;
import com.example.demo.management.entity.request.SelectOrdersRequest;
import com.example.demo.management.entity.response.SelectOrdersResponse;
import com.example.demo.management.mapper.OrdersMapper;
import com.example.demo.management.mapper.RequiresMapper;
import com.example.demo.management.service.AccountService;
import com.example.demo.management.service.MessageService;
import com.example.demo.management.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.management.service.TradingRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 订单信息表 服务实现类
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Resource
    OrdersMapper ordersMapper;

    @Resource
    RequiresMapper requiresMapper;

    @Resource
    TradingRecordService tradingRecordService;

    @Resource
    AccountService accountService;

    @Resource
    MessageService messageService;

    @Override
    public void addOrders(AddOrderRequest addOrderRequest, String loginId) {
        Orders orders = new Orders();
        BeanUtils.copyProperties(addOrderRequest,orders);
        orders.setDriverId(loginId);
        orders.setOrderId(Prefix.ORDER_PREFIX + UUID.randomUUID());

        Requires requires1 = requiresMapper.selectOne(Wrappers.lambdaQuery(Requires.class)
                .eq(Requires::getRequireId, addOrderRequest.getRequireId()));

        orders.setFees(requires1.getFees());


        Boolean isDelete = requiresMapper.selectOne(
                Wrappers.lambdaQuery(Requires.class)
                        .eq(Requires::getRequireId,addOrderRequest.getRequireId())
        ).getIsDelete();

        if(isDelete) {
            throw new CustomException(1,"该订单已失效，请刷新");
        }

        //需求表改变，表示该需求已形成订单
        Requires requires = new Requires();

        //选中的标志位
        requires.setStatus(Common.REQUIRE_CHOOSE);
        LambdaQueryWrapper<Requires> qw = new LambdaQueryWrapper<>();
        qw.eq(Requires::getRequireId,addOrderRequest.getRequireId());
        requiresMapper.update(requires,qw);

        ordersMapper.insert(orders);

//
        //添加第一类消息记录  您的订单已有人接单
        AddMessageRequest addMessageRequest = new AddMessageRequest();
        addMessageRequest.setLoginId(addOrderRequest.getCustomerId());
        addMessageRequest.setFromUserId(loginId);
        addMessageRequest.setGoodId(addOrderRequest.getRequireId());
        addMessageRequest.setStatus(1);
        messageService.addMessage(addMessageRequest);


    }

    @Override
    public void modifyOrders(AddOrderRequest addOrderRequest, String loginId) {
        Orders orders = new Orders();
        orders.setState(addOrderRequest.getStatus());
        LambdaQueryWrapper<Orders> qw = new LambdaQueryWrapper<>();
        qw.eq(Orders::getOrderId, addOrderRequest.getOrderId());

        ordersMapper.update(orders,qw);
        orders = ordersMapper.selectOne(Wrappers.lambdaQuery(Orders.class)
                .eq(Orders::getOrderId,addOrderRequest.getOrderId()));

        if (addOrderRequest.getStatus().equals(Common.ARRIVAL)) {
            //添加第二类消息记录,(顾客)您的订单 <b>"+message.getGoodName()+"</b> 已到达,快去支付
            AddMessageRequest addMessageRequest = new AddMessageRequest();
            addMessageRequest.setLoginId(orders.getCustomerId());
            addMessageRequest.setFromUserId(loginId);
            addMessageRequest.setGoodId(orders.getOrderId());
            addMessageRequest.setStatus(2);
            messageService.addMessage(addMessageRequest);
        }

        //2表示货主确认订单，可以钱可以到司机的账上了
        if (addOrderRequest.getStatus().equals(Common.PAID)) {

            //添加第三类消息记录（配送人员）您的订单已到账
            AddMessageRequest addMessageRequest = new AddMessageRequest();
            addMessageRequest.setLoginId(orders.getDriverId());
            addMessageRequest.setFromUserId(loginId);
            addMessageRequest.setGoodId(orders.getOrderId());
            addMessageRequest.setStatus(3);
            messageService.addMessage(addMessageRequest);
        }
    }

    @Override
    public void deleteOrders(String orderId) {
        Orders orders = ordersMapper.selectOne(Wrappers.lambdaQuery(Orders.class)
                .eq(Orders::getOrderId, orderId));
        if (orders == null) {
            throw new CustomException("该订单不存在");
        }
        ordersMapper.delete(Wrappers.lambdaQuery(Orders.class)
                .eq(Orders::getOrderId, orderId));
    }

    @Override
    public void cancelOrders(String orderId) {
        Orders orders = ordersMapper.selectOne(Wrappers.lambdaQuery(Orders.class)
                .eq(Orders::getOrderId, orderId));
        if (orders == null) {
            throw new CustomException("该订单不存在");
        }
        Requires requires = requiresMapper.selectOne(Wrappers.lambdaQuery(Requires.class)
                .eq(Requires::getRequireId,orders.getRequireId()));
        //撤销订单，原需求恢复
        requires.setStatus(0);
        requiresMapper.updateById(requires);
        ordersMapper.delete(Wrappers.lambdaQuery(Orders.class)
                .eq(Orders::getOrderId, orderId));
    }

    @Override
    public IPage<SelectOrdersResponse> selectDriverOrders(SelectOrdersRequest selectOrdersRequest, String driverId) {
        IPage<SelectOrdersResponse> queryPageRequest = new Page<>();
        queryPageRequest.setCurrent(selectOrdersRequest.getPageRequest().getCurrent());
        queryPageRequest.setSize(selectOrdersRequest.getPageRequest().getSize());

        IPage<SelectOrdersResponse> page  = ordersMapper.selectDriverOrders(queryPageRequest, selectOrdersRequest,driverId);

        return page;
    }

    @Override
    public IPage<SelectOrdersResponse> selectCustomerOrders(SelectOrdersRequest selectOrdersRequest,String customerId) {
        IPage<SelectOrdersResponse> queryPageRequest = new Page<>();
        queryPageRequest.setCurrent(selectOrdersRequest.getPageRequest().getCurrent());
        queryPageRequest.setSize(selectOrdersRequest.getPageRequest().getSize());

        IPage<SelectOrdersResponse> page  = ordersMapper.selectCustomerOrders(queryPageRequest, selectOrdersRequest,customerId);

        return page;
    }

    @Override
    public IPage<SelectOrdersResponse> selectAllOrders(SelectOrdersRequest selectOrdersRequest) {
        IPage<SelectOrdersResponse> queryPageRequest = new Page<>();
        queryPageRequest.setCurrent(selectOrdersRequest.getPageRequest().getCurrent());
        queryPageRequest.setSize(selectOrdersRequest.getPageRequest().getSize());
        IPage<SelectOrdersResponse> selectOrdersResponseIPage = ordersMapper.selectAllOrders(queryPageRequest, selectOrdersRequest);
        return selectOrdersResponseIPage;
    }
}
