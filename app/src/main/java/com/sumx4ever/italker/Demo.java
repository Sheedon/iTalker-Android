package com.sumx4ever.italker;

import com.sumx4ever.factory.data.DataSource;
import com.sumx4ever.italker.factory.data.helper.AccountHelper;
import com.sumx4ever.italker.factory.model.db.User;

/**
 * @Description: java类作用描述
 * @Author: sheedon
 * @Email: sheedonsun@163.com
 * @Date: 2020/2/25 22:18
 */
public class Demo {
    public static void main(String[] args) {
        AccountHelper.bindPush(new DataSource.Callback<User>() {
            @Override
            public void onDataNotAvailable(int strRes) {

            }

            @Override
            public void onDataLoaded(User user) {

            }
        });
    }
}
