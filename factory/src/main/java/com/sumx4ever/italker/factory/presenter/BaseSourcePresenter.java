package com.sumx4ever.italker.factory.presenter;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.sumx4ever.factory.data.DataSource;
import com.sumx4ever.factory.data.DbDataSource;
import com.sumx4ever.factory.presenter.BaseContract;
import com.sumx4ever.factory.presenter.BaseRecyclerPresenter;
import com.sumx4ever.italker.factory.data.helper.DbHelper;
import com.sumx4ever.italker.factory.model.db.BaseDbModel;
import com.sumx4ever.utils.CollectionUtil;


import net.qiujuer.genius.kit.reflect.Reflector;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 基础的仓库源的Presenter定义
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public abstract class BaseSourcePresenter<Data, ViewModel,
        Source extends DbDataSource<Data>,
        View extends BaseContract.RecyclerView>
        extends BaseRecyclerPresenter<ViewModel, View>
        implements DataSource.SucceedCallback<List<Data>> {

    protected Source mSource;

    public BaseSourcePresenter(Source source, View view) {
        super(view);
        this.mSource = source;
    }

    @Override
    public void start() {
        super.start();
        if (mSource != null)
            mSource.load(this);
    }

    @Override
    public void destroy() {
        super.destroy();
        mSource.dispose();
        mSource = null;
    }
}