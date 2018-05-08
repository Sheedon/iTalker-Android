package com.sumx4ever.italker.frags.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sumx4ever.common.app.BaseFragment;
import com.sumx4ever.common.widget.GalleyView;
import com.sumx4ever.italker.R;

import butterknife.BindView;


public class ActiveFragment extends BaseFragment {

    @BindView(R.id.galleyView)
    GalleyView mGalleyView;

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
        mGalleyView.setup(getLoaderManager(), new GalleyView.SelectedChangeListener() {
            @Override
            public void onSelectedCountChanged(int count) {

            }
        });
    }
}
