package com.sumx4ever.italker.factory.presenter.message;

import com.sumx4ever.factory.presenter.BaseContract;
import com.sumx4ever.italker.factory.model.db.Session;

/**
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2019/3/19
 */
public interface SessionContract {
    interface Presenter extends BaseContract.Presenter{

    }

    // 界面的基类
    interface View extends BaseContract.RecyclerView<Presenter,Session>{
    }

}
