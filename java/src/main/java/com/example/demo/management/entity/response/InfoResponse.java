package com.example.demo.management.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@ApiModel("获取货主信息实体类")
public class InfoResponse {
    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "电话")
    private String telephone;

    @ApiModelProperty(value = "常用住址")
    private String address;

    @ApiModelProperty(value = "头像地址")
    private String headPicUrl;

    @ApiModelProperty(value = "学院")
    private String college;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "学号")
    private String studentNumber;

    @ApiModelProperty(value = "班级")
    private String classes;

    @ApiModelProperty(value = "账户")
    private String account;
}
