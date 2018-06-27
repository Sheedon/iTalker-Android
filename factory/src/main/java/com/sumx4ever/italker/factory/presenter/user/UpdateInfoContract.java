package com.sumx4ever.italker.factory.presenter.user;

import com.sumx4ever.factory.presenter.BaseContract;

/**
 * 更新用户的基本信息
 *
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2018/6/26
 */
public interface UpdateInfoContract {
    interface Presenter extends BaseContract.Presenter {
        // 更新
        void update(String photoFilePath, String desc, boolean isMan);
    }

    interface View extends BaseContract.View<Presenter> {
        // 回调成功
        void updateSuc();
    }
}
