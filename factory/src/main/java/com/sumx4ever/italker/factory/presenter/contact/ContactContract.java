package com.sumx4ever.italker.factory.presenter.contact;

import com.sumx4ever.factory.presenter.BaseContract;
import com.sumx4ever.italker.factory.model.db.User;

/**
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2018/8/8
 */
public interface ContactContract {
    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter {

    }

    // 都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter, User> {

    }
}
