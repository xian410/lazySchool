package com.example.demo.management.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Data
@ApiModel("添加司机意向实体类")
public class AddDriverIntentionRequest {

    @ApiModelProperty(value = "货物编号")
    @NotBlank(message = "货物编号不为空")
    private String goodId;

    @ApiModelProperty(value = "货主id")
    @NotBlank(message = "货主id不为空")
    private String customerId;

    @ApiModelProperty(value = "运费报价")
    private Float fees;

    @ApiModelProperty(value = "备注")
    private String remark;
}
