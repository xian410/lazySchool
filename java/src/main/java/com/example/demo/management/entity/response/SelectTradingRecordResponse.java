package com.example.demo.management.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("获取交易记录实体类")
public class SelectTradingRecordResponse {

    @ApiModelProperty(value = "交易编号")
    private String tradeId;

    @ApiModelProperty(value = "订单编号")
    private String orderId;

    @ApiModelProperty(value = "用户主键")
    private String loginId;

    @ApiModelProperty(value = "自己姓名")
    private String myName;

    @ApiModelProperty(value = "来源")
    private String sources;

    @ApiModelProperty(value = "别人姓名")
    private String name;

    @ApiModelProperty(value = "类型")
    private Integer status;

    @ApiModelProperty(value = "钱数")
    private Float fees;

    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
