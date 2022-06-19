package com.example.demo.management.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 需求信息表
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Requires对象", description="需求信息表")
public class Requires implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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

    @ApiModelProperty(value = "是否已形成订单")
    private int status;
    
    @TableLogic(value = "0", delval = "1")
    @TableField(value = "is_delete", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否删除")
    private Boolean isDelete;

    @ApiModelProperty(value = "笔记")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
