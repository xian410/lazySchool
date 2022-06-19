package com.example.demo.management.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel("消息返回实体类")
@Data
public class SelectMessageResponse {

    @ApiModelProperty(value = "消息编号")
    private String messageId;

    @ApiModelProperty(value = "用户主键")
    private String loginId;

    @ApiModelProperty(value = "货物主键")
    private String goodId;

    @ApiModelProperty(value = "货物名称")
    private String goodName;

    @ApiModelProperty(value = "来源用户Id")
    private String fromUserId;

    @ApiModelProperty(value = "来源用户姓名")
    private String fromUserName;

    @ApiModelProperty(value = "消息类型 0未知 1.您的订单已有人接单，2.订单已到达 3.已到账")
    private Integer status;

    @ApiModelProperty(value = "消息")
    private String message;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDelete;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

}
