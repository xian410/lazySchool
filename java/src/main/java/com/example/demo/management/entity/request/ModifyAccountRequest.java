package com.example.demo.management.entity.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("修改账户余额")
public class ModifyAccountRequest {

    @ApiModelProperty("钱")
    private Integer money;

    @ApiModelProperty("顾客id")
    private String customerId;

    @ApiModelProperty("配送人员id")
    private String driverId;
}
