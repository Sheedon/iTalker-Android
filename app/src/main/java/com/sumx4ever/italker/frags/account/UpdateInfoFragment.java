package com.sumx4ever.italker.frags.account;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.sumx4ever.common.app.BaseFragment;
import com.sumx4ever.common.widget.GalleryView;
import com.sumx4ever.common.widget.PortraitView;
import com.sumx4ever.italker.App;
import com.sumx4ever.italker.R;
import com.sumx4ever.italker.frags.media.GalleryFragment;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * 更新信息的界面
 */
public class UpdateInfoFragment extends BaseFragment {

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    public UpdateInfoFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }

    @OnClick(R.id.im_portrait)
    void onPortrait() {
        new GalleryFragment().setListener(new GalleryFragment.OnSelectedListener() {
            @Override
            public void onSelectedImage(String path) {
                Log.v("SXD", "path:" + path);
                UCrop.Options options = new UCrop.Options();
                // 设置图片处理的格式JPEG
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                // 设置压缩后的图片精度
                options.setCompressionQuality(96);

                // 得到头像的缓存地址
                File dPath = App.getPortraitTmpFile();

                UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                        .withAspectRatio(1, 1) // 1:1比例
                        .withMaxResultSize(520, 520) // 最大的尺寸
                        .withOptions(options) // 相关参数
                        .start(getActivity());
            }
        })
                // show的时候建议使用getChildFragmentManager,
                // tag GalleryFragment class 名字
                .show(getChildFragmentManager(), GalleryFragment.class.getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 收到从Activity传递过来的回调，然后取出其中的值进行图片加载
        // 如果是我能够处理的类型
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            // 通过UCrop得到对应的Uri
            final Uri resultUri = UCrop.getOutput(data);
            if(resultUri != null){
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    /**
     * 加载Uri到当前的头像中
     * @param uri Uri
     */
    private void loadPortrait(Uri uri){
        Glide.with(this)
                .asBitmap()
                .load(uri)
                .apply(new RequestOptions().centerCrop())
                .into(mPortrait);
    }
}
