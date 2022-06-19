package com.example.demo.management.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.common.ApiResponse;
import com.example.demo.common.annotation.AccessToken;
import com.example.demo.common.exception.CustomException;
import com.example.demo.management.entity.LoginUser;
import com.example.demo.management.entity.request.ReleaseRequireRequest;
import com.example.demo.management.entity.request.SelectRequiresRequest;
import com.example.demo.management.entity.response.SelectRequiresListResponse;
import com.example.demo.management.service.RequiresService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 需求信息表 前端控制器
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
@Api(tags = "需求相关")
@CrossOrigin
@AccessToken
@RestController
@RequestMapping("/management/requires")
public class RequiresController {
    @Resource
    RequiresService requiresService;

    @PostMapping("/releaseRequire")
    @ApiOperation("发布需求")
    public ApiResponse<Object> ReleaseRequire(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @Validated @RequestBody ReleaseRequireRequest releaseRequireRequest
    ) {
        requiresService.releaseRequire(releaseRequireRequest,loginUser.getLoginId());
        return ApiResponse.success();
    }

    @PostMapping("/modifyRequire")
    @ApiOperation("修改需求")
    public ApiResponse<Object> modifyRequire(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @Validated @RequestBody ReleaseRequireRequest releaseRequireRequest
    ) {
        requiresService.modifyRequire(releaseRequireRequest,loginUser.getLoginId());
        return ApiResponse.success();
    }

    @PostMapping("/deleteRequire")
    @ApiOperation("删除需求")
    public ApiResponse<Object> deleteRequire(
            @ApiParam(name = "requireId") @RequestBody Map<String, Object> mp
    ) {
        String requireId = (String) mp.get("requireId");
        try {
            requiresService.deleteRequire(requireId);
        }
        catch (CustomException e) {
            return ApiResponse.error(e.getMsg());
        }
        return ApiResponse.success();
    }

    @PostMapping("/delRequireByManager")
    @ApiOperation("删除需求(管理员)")
    public ApiResponse<Object> delRequireByManager(
            @ApiParam(name = "requireId") @RequestBody Map<String, Object> mp
    ) {
        String requireId = (String) mp.get("requireId");
        try {
            requiresService.delRequireByManager(requireId);
        }
        catch (CustomException e) {
            return ApiResponse.error(e.getMsg());
        }
        return ApiResponse.success();
    }

    @PostMapping("/selectAllRequires")
    @ApiOperation("配送人员查看所有需求")
    public ApiResponse<IPage<SelectRequiresListResponse>> selectAllGoods(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @RequestBody SelectRequiresRequest selectRequiresRequest
    ) {
        IPage<SelectRequiresListResponse> page = requiresService.selectAllRequires(selectRequiresRequest,loginUser.getLoginId());
        return ApiResponse.success(page);
    }

//    @PostMapping("/selectAllTrueRequires")
//    @ApiOperation("管理员查看所有需求")
//    public ApiResponse<IPage<SelectGoodListResponse>> selectAllTrueRequires(
//            @RequestBody SelectGoodsRequest selectGoodsRequest
//    ) {
//        IPage<SelectGoodListResponse> page = goodsService.selectAllTrueGoods(selectGoodsRequest);
//        return ApiResponse.success(page);
//    }

    @PostMapping("/selectMyRequires")
    @ApiOperation("查看自己发布的需求")
    public ApiResponse<IPage<SelectRequiresListResponse>> selectMyRequires(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @RequestBody SelectRequiresRequest selectRequiresRequest
    ) {
        IPage<SelectRequiresListResponse> page = requiresService.selectMyRequires(selectRequiresRequest,loginUser.getLoginId());
        return ApiResponse.success(page);
    }

    @PostMapping("/selectDeletedRequires")
    @ApiOperation("查看自己已经取消的需求")
    public ApiResponse<IPage<SelectRequiresListResponse>> selectDeletedRequires(
            @ApiIgnore @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @RequestBody SelectRequiresRequest selectRequiresRequest
    ) {
        IPage<SelectRequiresListResponse> page = requiresService.selectDeletedRequires(selectRequiresRequest,loginUser.getLoginId());
        return ApiResponse.success(page);
    }
}

