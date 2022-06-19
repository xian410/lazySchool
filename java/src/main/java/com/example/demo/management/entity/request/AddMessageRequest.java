package com.example.demo.management.entity.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("添加消息实体类")
@Data
public class AddMessageRequest {

    @ApiModelProperty(value = "用户主键")
    private String loginId;

    @ApiModelProperty(value = "货物主键")
    private String goodId;

    @ApiModelProperty(value = "来源用户Id")
    private String fromUserId;

    @ApiModelProperty(value = "消息类型 0未知 1.有司机提出报价，2.货物已到达 3.报价被货主接受 4.被货主拒绝 5.已到账")
    private Integer status;
}
