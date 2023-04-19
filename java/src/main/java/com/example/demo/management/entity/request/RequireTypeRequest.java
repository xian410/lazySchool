package com.example.demo.management.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("需求类型id实体类")
public class RequireTypeRequest {

    @ApiModelProperty(value = "类型id")
    String typeId;
}
