package com.example.demo.management.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@ApiModel("发布需求请求实体类")
public class ReleaseRequireRequest {

    @ApiModelProperty(value = "用户id")
    private String loginId;

    @ApiModelProperty(value = "需求编号")
    private String requireId;

    @ApiModelProperty(value = "需求名称")
    private String name;

    @ApiModelProperty(value = "需求类型编号")
    private String typeId;

    @ApiModelProperty(value = "需求详细内容")
    private String detail;

    @ApiModelProperty(value = "是否限定男女，0不限定1男2女")
    private Integer genderLimited;

    @ApiModelProperty(value = "起点")
    private String start;

    @ApiModelProperty(value = "目的地点")
    private String destination;

    @ApiModelProperty(value = "运费要求")
    private Integer fees;

    @ApiModelProperty(value = "最晚时间")
    private LocalDateTime deadline;

    @ApiModelProperty(value = "备注")
    private String remark;
}
