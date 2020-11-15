package com.sumx4ever.italker.factory.presenter.group;

import com.sumx4ever.factory.presenter.BaseRecyclerPresenter;
import com.sumx4ever.italker.factory.Factory;
import com.sumx4ever.italker.factory.data.helper.GroupHelper;
import com.sumx4ever.italker.factory.model.db.view.MemberUserModel;
import java.util.List;

/**
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2019/4/23
 */
public class GroupMembersPresenter extends BaseRecyclerPresenter<MemberUserModel,
        GroupMembersContract.View>
        implements GroupMembersContract.Presenter {

    public GroupMembersPresenter(GroupMembersContract.View view) {
        super(view);
    }

    @Override
    public void refresh() {
        // 显示Loading
        start();

        // 异步加载
        Factory.runOnAsync(loader);
    }

    private Runnable loader = new Runnable() {
        @Override
        public void run() {
            GroupMembersContract.View view = getView();
            if (view == null)
                return;

            String groupId = view.getGroupId();
            // 传递数量为-1 代表查询所有
            List<MemberUserModel> models = GroupHelper.getMemberUsers(groupId, -1);

            refreshData(models);
        }
    };
}
