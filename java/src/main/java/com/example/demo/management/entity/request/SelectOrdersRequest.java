package com.example.demo.management.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("查询货物实体类")
public class SelectOrdersRequest {

    @ApiModelProperty("订单类型")
    private String status;

    @ApiModelProperty("分页请求")
    private PageRequest pageRequest;

}
