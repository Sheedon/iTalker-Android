package com.sumx4ever.italker.factory.presenter.account;

import com.sumx4ever.factory.presenter.BaseContract;

/**
 * @author Sumx stuby of qiujuer's MVP
 * @createDate 2018/5/31
 */
public interface LoginContract {

    interface View extends BaseContract.View<Presenter>{
        // 注册成功
        void loginSuc();
    }

    interface Presenter extends BaseContract.Presenter{
        // 发起一个注册
        void login(String phone, String password);
    }
}
