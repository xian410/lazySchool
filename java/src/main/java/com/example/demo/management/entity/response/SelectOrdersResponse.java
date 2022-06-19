package com.example.demo.management.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value="查看订单响应类")
public class SelectOrdersResponse {

    @ApiModelProperty(value = "订单编号")
    private String orderId;

    @ApiModelProperty(value = "需求编号")
    private String requireId;

    @ApiModelProperty(value = "需求名称")
    private String requireName;

    @ApiModelProperty(value = "需求类型编号")
    private String typeId;

    @ApiModelProperty(value = "需求类型名称")
    private String typeName;

    @ApiModelProperty(value = "需求详情")
    private String detail;

    @ApiModelProperty(value = "顾客id")
    private String customerId;

    @ApiModelProperty(value = "顾客姓名")
    private String customerName;

    @ApiModelProperty(value = "发布者头像")
    private String customerHeadPic;

    @ApiModelProperty(value = "顾客性别")
    private String customerGender;

    @ApiModelProperty(value = "顾客电话")
    private String customerTel;

    @ApiModelProperty(value = "顾客学院")
    private String customerColl;

    @ApiModelProperty(value = "配送人员id")
    private String driverId;

    @ApiModelProperty(value = "配送人员姓名")
    private String driverName;

    @ApiModelProperty(value = "配送人员头像")
    private String driverHeadPic;

    @ApiModelProperty(value = "配送人员性别")
    private String driverGender;

    @ApiModelProperty(value = "配送人员电话")
    private String driverTel;

    @ApiModelProperty(value = "配送人员学院")
    private String driverColl;

    @ApiModelProperty(value = "起点")
    private String start;

    @ApiModelProperty(value = "终点")
    private String destination;

    @ApiModelProperty(value = "运费报价")
    private Float fees;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "截止时间")
    private LocalDateTime deadline;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDelete;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
