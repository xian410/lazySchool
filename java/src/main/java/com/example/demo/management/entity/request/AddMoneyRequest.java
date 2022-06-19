package com.example.demo.management.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddMoneyRequest {
    @ApiModelProperty(value = "金额")
    private Float money;
}
