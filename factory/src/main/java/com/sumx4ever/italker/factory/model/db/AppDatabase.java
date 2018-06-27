package com.sumx4ever.italker.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * 数据库基本信息
 *
 * @author Sumx
 * @createDate 2018/6/12
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    public static final String NAME = "AppDatabase";
    public static final int VERSION = 1;
}
