package com.example.demo.management.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.management.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.management.entity.request.SelectOrdersRequest;
import com.example.demo.management.entity.response.SelectOrdersResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
public interface OrdersMapper extends BaseMapper<Orders> {
    /**
     * 司机查看订单
     */
    IPage<SelectOrdersResponse> selectDriverOrders(IPage<SelectOrdersResponse> queryPageRequest,
                                                   @Param("selectOrdersRequest") SelectOrdersRequest selectOrdersRequest,
                                                   @Param("loginId") String loginId);

    /**
     * 货主查看订单
     */
    IPage<SelectOrdersResponse> selectCustomerOrders(IPage<SelectOrdersResponse> queryPageRequest,
                                                     @Param("selectOrdersRequest") SelectOrdersRequest selectOrdersRequest,
                                                     @Param("loginId") String loginId);

    IPage<SelectOrdersResponse> selectAllOrders(IPage<SelectOrdersResponse> queryPageRequest,
                                                @Param("selectOrdersRequest") SelectOrdersRequest selectOrdersRequest);
}
