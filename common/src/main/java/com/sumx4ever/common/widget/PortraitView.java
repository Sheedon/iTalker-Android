package com.sumx4ever.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import de.hdodenhof.circleimageview.CircleImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.sumx4ever.common.R;
import com.sumx4ever.factory.model.Author;

/**
 * 头像控件
 *
 * @author Sumx stuby of qiujuer's MVP
 */
public class PortraitView extends CircleImageView {
    public PortraitView(Context context) {
        super(context);
    }

    public PortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PortraitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setup(RequestManager manager, Author author) {
        if(author == null)
            return;
        //进行显示
        this.setup(manager, author.getPortrait());
    }

    public void setup(RequestManager manager, String url) {
        this.setup(manager, R.drawable.default_portrait, url);
    }

    public void setup(RequestManager manager, int resourceId, String url) {
        if(url == null)
            url = "";
        manager.load(url)
                .apply(new RequestOptions()
                        .centerCrop()
                        .placeholder(resourceId)
                        .dontAnimate())//CircleImageView 控件中不能使用渐变动画，会导致显示异常
                .into(this);
    }
}
