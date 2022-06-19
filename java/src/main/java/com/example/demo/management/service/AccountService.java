package com.example.demo.management.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.management.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.management.entity.LoginUser;
import com.example.demo.management.entity.request.GetAccountRequest;
import com.example.demo.management.entity.request.ModifyAccountRequest;
import com.example.demo.management.entity.response.AllAccountResponse;

/**
 * <p>
 * 账户信息表 服务类
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
public interface AccountService extends IService<Account> {
    /**
     * 修改账户金额
     * @param loginId 用户id
     * @param money 修改的金额
     */
    void modMoney(String loginId, Float money);

    /**
     * 充值金额
     * @param loginId 用户id
     * @param money 充值的金额
     */
    Float addMoney(String loginId, Float money);

    /**
     * 查询账户金额
     * @param loginId 用户id
     * @return
     */
    Float queryMoney(String loginId);

    /**
     * 管理员查看所有用户
     * @param getAccountRequest
     * @return
     */
//    IPage<AllAccountResponse> queryAll(GetAccountRequest getAccountRequest);

    /**
     * 根据用户id修改账户余额
     * @param loginId
     * @param money
     */
    void modMoneyById(String loginId, Float money);

    void modifyAccount(ModifyAccountRequest modifyAccountRequest);

    Float profile(LoginUser loginUser);

    Integer ordersCount(LoginUser loginUser);
}
