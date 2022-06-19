package com.example.lazysch.utils;

public class Apiurls {
   //服务器地址
   public static final String server = "http://localhost:8081";


    //登录
    public static final String login = "/management/login-user/login";

    //登出
    public static final String logout = "/management/login-user/logout";

    //注册
    public static final String register= "/management/login-user/register";

    //获取个人信息
    public static final String getInformation = "/management/information/getCustomerInfo";

    //完善个人信息
    public static final String completeInformation = "/management/information/modCustomerInfo";

    //发布需求
    public static final String releaseRequire = "/management/requires/releaseRequire";

    //取消需求
    public static final String deleteRequire = "/management/requires/deleteRequire";

    //形成订单
    public static final String addOrders = "/management/orders/addOrders";

    //修改订单订单状态
    public static final String modifyOrders = "/management/orders/modifyOrders";

    //取消订单
    public static final String cancelOrders = "/management/orders/cancelOrders";

    //删除订单
    public static final String delOrders = "/management/orders/delOrders";

    //配送人员查看所有需求
    public static final String selectAllRequires = "/management/requires/selectAllRequires";

    //顾客查看自己的需求（未接单）
    public static final String selectMyRequires = "/management/requires/selectMyRequires";

    //顾客查看自己历史的需求
    public static final String selectDeletedRequires = "/management/requires/selectDeletedRequires";

    //顾客查看自己的订单
    //0是全部，1是进行中，2是已完成待支付，3是已支付
    public static final String selectCustomerOrders = "/management/orders/selectCustomerOrders";

    //配送人员查看自己的订单
    public static final String selectDriverOrders = "/management/orders/selectDriverOrders";

    //查看消息
    public static final String selectMessage = "/management/message/selectMessage";

    //一键删除消息
    public static final String delMessage = "/management/message/delMessage";

    //修改账户
    public static final String modifyAccount = "/management/account/modifyAccount";

    //充值金额
    public static final String addMoney = "/management/account/addMoney";

    //用户上传头像
    public static final String uploadHeadPic = "/management/information/uploadHeadPic";

    //配送人员接单量
    public static final String ordersCount = "/management/account/ordersCount";

    //配送人员赚的钱
    public static final String profile = "/management/account/profile";
}
