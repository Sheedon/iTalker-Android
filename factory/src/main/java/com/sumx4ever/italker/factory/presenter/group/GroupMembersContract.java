package com.sumx4ever.italker.factory.presenter.group;

import com.sumx4ever.factory.presenter.BaseContract;
import com.sumx4ever.italker.factory.model.db.view.MemberUserModel;

/**
 * 群成员的契约
 *
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2019/4/23
 */
public interface GroupMembersContract {

    interface Presenter extends BaseContract.Presenter {
        // 具有一个刷新的方法
        void refresh();
    }

    // 界面
    interface View extends BaseContract.RecyclerView<Presenter, MemberUserModel> {
        String getGroupId();
    }
}
