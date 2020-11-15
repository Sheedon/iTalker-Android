package com.sumx4ever.italker.factory.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.sumx4ever.italker.factory.model.db.Session;
import com.sumx4ever.italker.factory.model.db.Session_Table;

/**
 * 会话辅助工具类
 *
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2018/9/10
 */
public class SessionHelp {
    // 从本地查询Session
    public static Session findFromLocal(String id) {
        return SQLite.select()
                .from(Session.class)
                .where(Session_Table.id.eq(id))
                .querySingle();
    }
}
