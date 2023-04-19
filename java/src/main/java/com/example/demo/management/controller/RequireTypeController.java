package com.example.demo.management.controller;


import com.example.demo.common.ApiResponse;
import com.example.demo.common.annotation.AccessToken;
import com.example.demo.management.entity.LoginUser;
import com.example.demo.management.entity.request.ReleaseRequireRequest;
import com.example.demo.management.entity.request.RequireTypeRequest;
import com.example.demo.management.service.RequireTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * <p>
 * 需求类型表 前端控制器
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */

@RestController
@RequestMapping("/management/require-type")
public class RequireTypeController {

    @Resource
    RequireTypeService requireTypeService;

    @PostMapping("/getRequireType")
    @ApiOperation(value = "获取需求类型")
    public ApiResponse<String> getRequireType(
            @Validated @RequestBody RequireTypeRequest requireTypeRequest
            ) {
        String requireTypeName = requireTypeService.getRequireType(requireTypeRequest.getTypeId());
        return ApiResponse.success("success",requireTypeName);
    }
}

