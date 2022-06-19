package com.example.demo.management.entity.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@ApiModel(value="查看货物列表响应类")
public class SelectRequiresListResponse {

    @ApiModelProperty(value = "用户id")
    private String loginId;

    @ApiModelProperty(value = "发布者姓名")
    private String userName;

    @ApiModelProperty(value = "发布者头像")
    private String headPic;

    @ApiModelProperty(value = "需求类型编号")
    private String requireId;

    @ApiModelProperty(value = "需求名称")
    private String requireName;

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

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
