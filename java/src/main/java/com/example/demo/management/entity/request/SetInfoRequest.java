package com.example.demo.management.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("货主个人信息请求实体类")
public class SetInfoRequest {
    @ApiModelProperty(value = "用户主键")
    private String loginId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "学号")
    private String studentNumber;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "常用地址")
    private String address;

    @ApiModelProperty(value = "学院")
    private String college;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "班级")
    private String classes;
}
