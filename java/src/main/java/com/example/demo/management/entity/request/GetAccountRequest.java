package com.example.demo.management.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录请求实体类")
public class GetAccountRequest {
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("分页请求")
    private PageRequest pageRequest;
}
