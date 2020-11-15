package com.sumx4ever.italker.factory.data.message;

import com.sumx4ever.factory.data.DbDataSource;
import com.sumx4ever.italker.factory.model.db.Message;

/**
 * 消息到数据源定义，他的实现是：MessageRepository, MessageGroupRepository
 * 关注的对象是Message表
 *
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2019/3/17
 */
public interface MessageDataSource extends DbDataSource<Message> {
}
