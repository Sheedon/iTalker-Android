package com.sumx4ever.italker.factory.presenter.account;

import com.sumx4ever.factory.presenter.BaseContract;

/**
 * @author Sumx stuby of qiujuer's MVP
 * @createDate 2018/5/31
 */
public interface RegisterContract {

    interface View extends BaseContract.View<Presenter>{
        // 注册成功
        void registerSuc();
    }

    interface Presenter extends BaseContract.Presenter{
        // 发起一个注册
        void register(String phone,String password,String name);

        // 检查手机号是否正确
        boolean checkMoblie(String phone);
    }
}
