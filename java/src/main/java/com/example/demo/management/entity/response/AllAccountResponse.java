package com.example.demo.management.entity.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@ApiModel("获取所有账户信息")
public class AllAccountResponse {
    @ApiModelProperty(value = "用户主键")
    private String loginId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "用户类型")
    private String type;

    @ApiModelProperty(value = "用户余额")
    private float money;

    @ApiModelProperty(value = "上次登录时间")
    private LocalDateTime time;

    @ApiModelProperty(value = "上次登录时间")
    private String time1;
}
