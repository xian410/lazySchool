package com.example.demo.management.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel("消息返回实体类")
@Data
public class SelectDialogResponse {

    @ApiModelProperty(value = "消息编号")
    private String dialogueId;

    @ApiModelProperty(value = "用户主键")
    private String loginId;

    @ApiModelProperty(value = "对方用户Id")
    private String toUserId;

    @ApiModelProperty(value = "对话内容")
    private String details;

    @ApiModelProperty(value = "是否为自己发出的，0为自己发出的，1为对方发出的")
    private Integer status;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDelete;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
