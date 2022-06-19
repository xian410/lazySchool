package com.example.demo.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.exception.CustomException;
import com.example.demo.management.entity.Orders;
import com.example.demo.management.entity.Requires;
import com.example.demo.management.entity.constact.Prefix;
import com.example.demo.management.entity.request.ReleaseRequireRequest;
import com.example.demo.management.entity.request.SelectRequiresRequest;
import com.example.demo.management.entity.response.SelectRequiresListResponse;
import com.example.demo.management.mapper.InformationMapper;
import com.example.demo.management.mapper.OrdersMapper;
import com.example.demo.management.mapper.RequiresMapper;
import com.example.demo.management.service.RequiresService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 需求信息表 服务实现类
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
@Service
public class RequiresServiceImpl extends ServiceImpl<RequiresMapper, Requires> implements RequiresService {

    @Resource
    RequiresMapper requiresMapper;

    @Resource
    InformationMapper informationMapper;

    @Resource
    OrdersMapper ordersMapper;

    @Override
    public void releaseRequire(ReleaseRequireRequest releaseRequireRequest, String loginId) {
        Requires requires = new Requires();
        BeanUtils.copyProperties(releaseRequireRequest,requires);
        requires.setLoginId(loginId);
        requires.setIsDelete(false);
        requires.setRequireId(Prefix.REQUIRE_PREFIX + UUID.randomUUID());

        requiresMapper.insert(requires);
    }

    @Override
    public void modifyRequire(ReleaseRequireRequest releaseRequireRequest, String loginId) {
        Requires requires = new Requires();
        BeanUtils.copyProperties(releaseRequireRequest,requires);
        requiresMapper.update(requires,Wrappers.lambdaUpdate(Requires.class)
                .eq(Requires::getRequireId, requires.getRequireId()));
    }

    @Override
    public void deleteRequire(String requireId) {
        Requires requires = requiresMapper.selectOne(Wrappers.lambdaQuery(Requires.class)
                .eq(Requires::getRequireId, requireId));
        if (requires == null) {
            throw new CustomException("该需求不存在");
        }

        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("require_id", requireId);
        List<Orders> orders = ordersMapper.selectList(queryWrapper);
        if (orders.size() != 0) {
            throw new CustomException("订单已生成，请优先撤回订单");
        }

        requiresMapper.delete(Wrappers.lambdaQuery(Requires.class)
                .eq(Requires::getRequireId,requireId));
    }


    @Override
    public IPage<SelectRequiresListResponse> selectAllRequires(SelectRequiresRequest selectRequiresRequest, String loginId) {

        IPage<SelectRequiresListResponse> queryPageRequest = new Page<>();
        queryPageRequest.setCurrent(selectRequiresRequest.getPageRequest().getCurrent());
        queryPageRequest.setSize(selectRequiresRequest.getPageRequest().getSize());


        IPage<SelectRequiresListResponse> page  = requiresMapper.selectAllRequires(queryPageRequest, selectRequiresRequest);

        return page;
    }

    @Override
    public IPage<SelectRequiresListResponse> selectMyRequires(SelectRequiresRequest selectRequiresRequest, String loginId) {

        IPage<SelectRequiresListResponse> queryPageRequest = new Page<>();
        queryPageRequest.setCurrent(selectRequiresRequest.getPageRequest().getCurrent());
        queryPageRequest.setSize(selectRequiresRequest.getPageRequest().getSize());

        IPage<SelectRequiresListResponse> page  = requiresMapper.selectMyRequires(queryPageRequest, selectRequiresRequest,loginId);

        List<SelectRequiresListResponse> list = page.getRecords();

        page.setRecords(list);
        return page;

    }

    @Override
    public IPage<SelectRequiresListResponse> selectAllTrueRequires(SelectRequiresRequest selectRequiresRequest) {
        IPage<SelectRequiresListResponse> queryPageRequest = new Page<>();
        queryPageRequest.setCurrent(selectRequiresRequest.getPageRequest().getCurrent());
        queryPageRequest.setSize(selectRequiresRequest.getPageRequest().getSize());

        IPage<SelectRequiresListResponse> page  = requiresMapper.selectAllRequires(queryPageRequest, selectRequiresRequest);

        return page;
    }

    @Override
    public void delRequireByManager(String requireId) {
        Requires requires = requiresMapper.selectOne(Wrappers.lambdaQuery(Requires.class)
                .eq(Requires::getRequireId, requireId));
        if (requires == null) {
            throw new CustomException("该需求不存在");
        }
        requiresMapper.delete(Wrappers.lambdaQuery(Requires.class)
                .eq(Requires::getRequireId,requireId));
    }

    @Override
    public IPage<SelectRequiresListResponse> selectDeletedRequires(SelectRequiresRequest selectRequiresRequest, String loginId) {
        IPage<SelectRequiresListResponse> queryPageRequest = new Page<>();
        queryPageRequest.setCurrent(selectRequiresRequest.getPageRequest().getCurrent());
        queryPageRequest.setSize(selectRequiresRequest.getPageRequest().getSize());
        IPage<SelectRequiresListResponse> page = requiresMapper.selectDeletedRequires(queryPageRequest, selectRequiresRequest, loginId);
        List<SelectRequiresListResponse> list = page.getRecords();

        page.setRecords(list);
        return page;
    }
}
