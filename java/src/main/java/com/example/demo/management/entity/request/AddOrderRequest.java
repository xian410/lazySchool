package com.example.demo.management.entity.request;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Api("增加订单实体类")
public class AddOrderRequest {
    @ApiModelProperty(value = "订单编号")
    private String orderId;

    @ApiModelProperty(value = "货物编号")
    private String requireId;

    @ApiModelProperty(value = "顾客id")
    private String customerId;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
