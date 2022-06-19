package com.example.demo.management.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
@ApiModel("登录请求实体类")
public class LoginRequest {
    @ApiModelProperty(value = "登录主键")
    @NotBlank(message = "请输入您的账号")
    private String loginId;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "请输入您的密码")
    private String password;
}
