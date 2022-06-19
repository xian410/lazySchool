package com.example.demo.management.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("增加交易记录实体类")
public class AddTradingRecordRequest {
    @ApiModelProperty(value = "订单编号")
    private String orderId;

    @ApiModelProperty(value = "用户主键")
    private String loginId;

    @ApiModelProperty(value = "来源")
    private String sources;

    @ApiModelProperty(value = "类型")
    private Integer status;

    @ApiModelProperty(value = "钱数")
    private Float fees;
}
