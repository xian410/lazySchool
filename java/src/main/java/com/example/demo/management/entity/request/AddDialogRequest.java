package com.example.demo.management.entity.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("添加消息实体类")
@Data
public class AddDialogRequest {

    @ApiModelProperty(value = "用户主键")
    private String loginId;

    @ApiModelProperty(value = "目标用户Id")
    private String toUserId;

    @ApiModelProperty(value = "内容")
    private String details;

    @ApiModelProperty(value = "是否为自己的会话")
    private Integer status;
}
