package com.example.demo.management.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.management.entity.Requires;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.management.entity.request.SelectOrdersRequest;
import com.example.demo.management.entity.request.SelectRequiresRequest;
import com.example.demo.management.entity.response.SelectRequiresListResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 需求信息表 Mapper 接口
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
public interface RequiresMapper extends BaseMapper<Requires> {
    /**
     * 获取需求名称
     * @param requireId
     * @return
     */
    String getRequireName(@Param("requireId") String requireId);

    /**
     * 司机查看所有需求
     */
    IPage<SelectRequiresListResponse> selectAllRequires(IPage<SelectRequiresListResponse> queryPageRequest,
                                                        @Param("selectRequiresRequest")SelectRequiresRequest selectRequiresRequeste);

    /**
     * 用户查看自己的需求
     */
    IPage<SelectRequiresListResponse> selectMyRequires(IPage<SelectRequiresListResponse> queryPageRequest,
                                                      @Param("selectRequiresRequest") SelectRequiresRequest selectGoodsRequest,
                                                      @Param("loginId") String loginId);

    IPage<SelectRequiresListResponse> selectDeletedRequires(IPage<SelectRequiresListResponse> queryPageRequest,
                                                            @Param("selectRequiresRequest") SelectRequiresRequest selectRequiresRequest,
                                                            @Param("loginId") String loginId);
}
