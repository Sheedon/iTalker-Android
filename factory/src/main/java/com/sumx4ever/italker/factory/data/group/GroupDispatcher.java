package com.sumx4ever.italker.factory.data.group;

import com.sumx4ever.italker.factory.data.helper.DbHelper;
import com.sumx4ever.italker.factory.data.helper.GroupHelper;
import com.sumx4ever.italker.factory.data.helper.UserHelper;
import com.sumx4ever.italker.factory.model.card.GroupCard;
import com.sumx4ever.italker.factory.model.card.GroupMemberCard;
import com.sumx4ever.italker.factory.model.db.Group;
import com.sumx4ever.italker.factory.model.db.GroupMember;
import com.sumx4ever.italker.factory.model.db.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 群／群成员卡片中心的实现类
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class GroupDispatcher implements GroupCenter {
    private static GroupCenter instance;
    // 单线程池；处理卡片一个个的消息进行处理
    private final Executor executor = Executors.newSingleThreadExecutor();

    public static GroupCenter instance() {
        if (instance == null) {
            synchronized (GroupDispatcher.class) {
                if (instance == null) {
                    instance = new GroupDispatcher();
                }
            }
        }
        return instance;
    }

    @Override
    public void dispatch(GroupCard... cards) {
        if (cards == null || cards.length == 0)
            return;
        executor.execute(new GroupCardHandler(cards));
    }

    @Override
    public void dispatch(GroupMemberCard... cards) {
        if (cards == null || cards.length == 0)
            return;
        executor.execute(new GroupMemberCardHandler(cards));
    }

    /**
     * 把群Card处理为群DB类
     */
    private class GroupCardHandler implements Runnable {
        private final GroupCard[] cards;

        public GroupCardHandler(GroupCard[] cards) {
            this.cards = cards;
        }

        @Override
        public void run() {
            List<Group> groups = new ArrayList<>();
            for (GroupCard card : cards) {
                // 搜索管理员
                User owner = UserHelper.search(card.getOwnerId());
                if (owner != null) {
                    Group group = card.build(owner);
                    groups.add(group);
                }
            }
            if (groups.size() > 0) {
                DbHelper.save(Group.class, groups.toArray(new Group[0]));
            }

        }
    }

    private class GroupMemberCardHandler implements Runnable {
        private final GroupMemberCard[] cards;

        public GroupMemberCardHandler(GroupMemberCard[] cards) {
            this.cards = cards;
        }

        @Override
        public void run() {
            List<GroupMember> groupMembers = new ArrayList<>();
            for (GroupMemberCard card : cards) {
                // 成员对应的人的信息
                User user = UserHelper.search(card.getUserId());
                // 成员对应的群的信息
                Group group = GroupHelper.find(card.getGroupId());
                if (user != null && group != null) {
                    GroupMember member = card.build(group, user);
                    groupMembers.add(member);
                }
            }

            if (groupMembers.size() > 0)
                DbHelper.save(GroupMember.class, groupMembers.toArray(new GroupMember[0]));

        }
    }
}
