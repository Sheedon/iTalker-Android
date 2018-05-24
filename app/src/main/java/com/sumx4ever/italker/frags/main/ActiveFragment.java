package com.sumx4ever.italker.frags.main;


import com.sumx4ever.common.app.BaseFragment;
import com.sumx4ever.common.widget.GalleryView;
import com.sumx4ever.italker.R;

import butterknife.BindView;


public class ActiveFragment extends BaseFragment {

    @BindView(R.id.galleryView)
    GalleryView mGalleryView;

    public ActiveFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void initDate() {
        super.initDate();
        mGalleryView.setup(getLoaderManager(), new GalleryView.SelectedChangeListener() {
            @Override
            public void onSelectedCountChanged(int count) {

            }
        });
    }
}
