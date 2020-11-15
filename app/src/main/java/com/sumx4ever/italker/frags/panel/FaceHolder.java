package com.sumx4ever.italker.frags.panel;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.sumx4ever.common.widget.recycler.RecyclerAdapter;
import com.sumx4ever.face.Face;
import com.sumx4ever.italker.R;

import butterknife.BindView;

/**
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2019/4/25
 */
public class FaceHolder extends RecyclerAdapter.ViewHolder<Face.Bean> {
    @BindView(R.id.im_face)
    ImageView mFace;

    public FaceHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void onBind(Face.Bean bean) {
        if (bean != null
                // drawable 资源 id
                && (bean.preview instanceof Integer
                // face zip 包资源路径
                || bean.preview instanceof String))
            Glide.with(itemView.getContext())
                    .asBitmap()
                    .load(bean.preview)
                    .apply(new RequestOptions()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .placeholder(R.drawable.default_face))
                    .into(mFace);
    }
}
