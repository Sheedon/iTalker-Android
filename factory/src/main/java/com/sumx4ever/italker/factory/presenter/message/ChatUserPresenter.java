package com.sumx4ever.italker.factory.presenter.message;

import com.sumx4ever.italker.factory.data.helper.UserHelper;
import com.sumx4ever.italker.factory.data.message.MessageRepository;
import com.sumx4ever.italker.factory.model.db.Message;
import com.sumx4ever.italker.factory.model.db.User;

/**
 * 用户聊天的逻辑实现
 *
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2019/3/17
 */
public class ChatUserPresenter extends ChatPresenter<ChatContract.UserView>
        implements ChatContract.Presenter {

    public ChatUserPresenter(ChatContract.UserView view, String
            receiverId) {
        // 数据源，View，接收者，接收者的类型
        super(new MessageRepository(receiverId), view, receiverId, Message.RECEIVER_TYPE_NONE);

    }

    @Override
    public void start() {
        super.start();

        // 从本地那这个人的信息
        User receiver = UserHelper.findFromLocal(mReceiverId);
        getView().onInit(receiver);
    }
}
