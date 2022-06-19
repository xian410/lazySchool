package com.example.demo.management.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("查询货物实体类")
public class SelectRequiresRequest {

    @ApiModelProperty("起点")
    private String start;

    @ApiModelProperty("需求名称")
    private String name;

    @ApiModelProperty("终点")
    private String destination;

    @ApiModelProperty(value = "需求类型编号")
    private String typeId;

    @ApiModelProperty("分页请求")
    private PageRequest pageRequest;


}
