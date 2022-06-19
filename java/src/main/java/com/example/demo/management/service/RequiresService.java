package com.example.demo.management.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.management.entity.Requires;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.management.entity.request.ReleaseRequireRequest;
import com.example.demo.management.entity.request.SelectRequiresRequest;
import com.example.demo.management.entity.response.SelectRequiresListResponse;

/**
 * <p>
 * 需求信息表 服务类
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
public interface RequiresService extends IService<Requires> {
    /**
     * 发布需求
     */
    void releaseRequire(ReleaseRequireRequest releaseRequireRequest, String loginId);

    /**
     * 修改货物
     */
    void modifyRequire(ReleaseRequireRequest releaseRequireRequest, String loginId);

    /**
     * 删除货物
     * @param requireId
     */
    void deleteRequire(String requireId);

    /**
     * 配送人员查看所有货物
     */
    IPage<SelectRequiresListResponse> selectAllRequires(SelectRequiresRequest selectRequiresRequest, String loginId);


    /**
     * 用户查看自己发布的需求
     */
    IPage<SelectRequiresListResponse> selectMyRequires(SelectRequiresRequest selectRequiresRequest, String loginId);

    /**
     * 管理员查看所有货物
     * @param selectRequiresRequest
     * @return
     */
    IPage<SelectRequiresListResponse> selectAllTrueRequires(SelectRequiresRequest selectRequiresRequest);

    /**
     * 管理员删除货物
     * @param requireId
     */
    void delRequireByManager(String requireId);

    IPage<SelectRequiresListResponse> selectDeletedRequires(SelectRequiresRequest selectRequiresRequest, String loginId);
}
