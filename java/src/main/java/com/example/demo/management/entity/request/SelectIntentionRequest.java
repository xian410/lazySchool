package com.example.demo.management.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("查询货物实体类")
public class SelectIntentionRequest {

    @ApiModelProperty(value = "货物名称")
    private String name;

    @ApiModelProperty(value = "货物类型编号")
    private String typeId;

    @ApiModelProperty("分页请求")
    private PageRequest pageRequest;

    @ApiModelProperty("运单状态")
    private Integer status;



}
