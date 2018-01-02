package com.sumx4ever.common.widget.recycler;

/**
 * @author xudongsun
 */

public interface AdapterCallback<Data> {
    void update(Data data,RecyclerAdapter.ViewHolder<Data> holder);
}
