package com.sumx4ever.italker.frags.panel;


import android.view.View;

import com.sumx4ever.common.widget.recycler.RecyclerAdapter;
import com.sumx4ever.face.Face;
import com.sumx4ever.italker.R;

import java.util.List;

/**
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2019/4/25
 */
public class FaceAdapter extends RecyclerAdapter<Face.Bean> {

    public FaceAdapter(List<Face.Bean> beans, AdapterListener<Face.Bean> listener) {
        super(beans, listener);
    }

    @Override
    protected int getItemViewType(int position, Face.Bean bean) {
        return R.layout.cell_face;
    }

    @Override
    protected ViewHolder<Face.Bean> onCreateViewHolder(View root, int viewType) {
        return new FaceHolder(root);
    }
}
