package com.sumx4ever.italker.factory.presenter.contact;

import com.sumx4ever.factory.presenter.BaseContract;
import com.sumx4ever.italker.factory.model.db.User;

/**
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2018/8/16
 */
public interface PersonalContract {

    interface Presenter extends BaseContract.Presenter {
        // 获取用户信息
        User getUserPersonal();
    }

    interface View extends BaseContract.View<Presenter> {
        String getUserId();

        // 加载数据完成
        void onLoadDone(User user);

        // 是否发起聊天
        void allowSayHello(boolean isAllow);

        // 设置关注信息
        void setFollowStatus(boolean isFollow);
    }
}
