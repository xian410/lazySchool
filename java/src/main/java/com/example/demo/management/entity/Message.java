package com.example.demo.management.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统消息表
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Message对象", description="系统消息表")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "消息编号")
    private String messageId;

    @ApiModelProperty(value = "用户主键")
    private String loginId;

    @ApiModelProperty(value = "货物主键")
    private String goodId;

    @ApiModelProperty(value = "来源用户Id")
    private String fromUserId;

    @ApiModelProperty(value = "消息类型 0未知 1.有司机提出报价，2.货物已到达 3.报价被货主接受 4.被货主拒绝 5.已到账")
    private Integer status;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDelete;

    @ApiModelProperty(value = "笔记")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
