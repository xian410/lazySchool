package com.example.demo.management.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分页请求实体类")
public class PageRequest {
    @ApiModelProperty(value = "当前页数")
    private long current;

    @ApiModelProperty(value = "当前页最大条数")
    private long size;
}
