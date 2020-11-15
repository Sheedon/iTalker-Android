package com.sumx4ever.italker.factory.presenter.contact;

import com.sumx4ever.factory.presenter.BaseContract;
import com.sumx4ever.italker.factory.model.card.UserCard;

/**
 * 关注的接口定义
 *
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2018/8/7
 */
public interface FollowContract {

    interface Presenter extends BaseContract.Presenter {
        // 关注一个人
        void follow(String id);
    }

    interface View extends BaseContract.View<Presenter> {
        // 成功的情况下返回一个用户的信息
        void onFollowSucceed(UserCard userCard);
    }
}
