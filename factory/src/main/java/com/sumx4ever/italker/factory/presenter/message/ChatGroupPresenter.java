package com.sumx4ever.italker.factory.presenter.message;

import com.sumx4ever.italker.factory.data.helper.GroupHelper;
import com.sumx4ever.italker.factory.data.helper.UserHelper;
import com.sumx4ever.italker.factory.data.message.MessageGroupRepository;
import com.sumx4ever.italker.factory.data.message.MessageRepository;
import com.sumx4ever.italker.factory.model.db.Group;
import com.sumx4ever.italker.factory.model.db.Message;
import com.sumx4ever.italker.factory.model.db.User;
import com.sumx4ever.italker.factory.model.db.view.MemberUserModel;
import com.sumx4ever.italker.factory.persistence.Account;

import java.util.List;

/**
 * 群聊天的逻辑实现
 *
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2019/3/17
 */
public class ChatGroupPresenter extends ChatPresenter<ChatContract.GroupView>
        implements ChatContract.Presenter {


    public ChatGroupPresenter(ChatContract.GroupView view, String
            receiverId) {
        // 数据源，View，接收者，接收者的类型
        super(new MessageGroupRepository(receiverId), view, receiverId, Message
                .RECEIVER_TYPE_GROUP);
    }


    @Override
    public void start() {
        super.start();

        // 从本地那这个群的信息
        Group group = GroupHelper.findFromLocal(mReceiverId);
        if (group != null) {
            // 初始化操作
            ChatContract.GroupView view = getView();

            boolean isAdmin = Account.getUserId().equalsIgnoreCase(group.getOwner().getId());
            view.showAdminOption(isAdmin);

            // 基础信息初始化
            view.onInit(group);

            // 成员初始化
            List<MemberUserModel> models = group.getLatelyGroupMembers();
            final long memberCount = group.getGroupMemberCount();
            // 没有显示的成员数量
            long moreCount = memberCount - models.size();
            view.onInitGroupMembers(models, moreCount);
        }
    }
}
