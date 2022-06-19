package com.example.demo.management.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录响应")
public class LoginInformationResponse {

    @ApiModelProperty(value = "令牌")
    private String token;

    @ApiModelProperty(value = "用户类型")
    private Integer userType;
}
