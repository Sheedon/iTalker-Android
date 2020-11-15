package com.sumx4ever.italker.frags.panel;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sumx4ever.common.app.Application;
import com.sumx4ever.common.app.Fragment;
import com.sumx4ever.common.tools.AudioRecordHelper;
import com.sumx4ever.common.tools.UiTool;
import com.sumx4ever.common.widget.AudioRecordView;
import com.sumx4ever.common.widget.GalleryView;
import com.sumx4ever.common.widget.recycler.RecyclerAdapter;
import com.sumx4ever.face.Face;
import com.sumx4ever.italker.R;

import net.qiujuer.genius.ui.Ui;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * 底部面板实现
 */
public class PanelFragment extends Fragment {
    private View mFacePanel, mGalleryPanel, mRecordPanel;
    private PanelCallback mCallback;

    public PanelFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_panel;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        initFace(root);
        initRecord(root);
        initGallery(root);
    }

    // 开始初始化方法
    public void setup(PanelCallback callback) {
        this.mCallback = callback;
    }

    // 初始化表情
    private void initFace(View root) {
        View facePanel = mFacePanel = root.findViewById(R.id.lay_panel_face);

        View backspace = facePanel.findViewById(R.id.im_backspace);
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除逻辑
                PanelCallback callback = mCallback;
                if (callback == null)
                    return;

                // 模拟一个键盘删除点击
                KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL,
                        0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                callback.getInputEditText().dispatchKeyEvent(event);
            }
        });


        TabLayout tabLayout = facePanel.findViewById(R.id.tab);

        ViewPager viewPager = facePanel.findViewById(R.id.pager);
        tabLayout.setupWithViewPager(viewPager);

        // 每一表情显示48dp
        final int minFaceSize = (int) Ui.dipToPx(getResources(), 48);
        final int totalScreen = UiTool.getScreenWidth(getActivity());
        final int spanCount = totalScreen / minFaceSize;

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Face.all(getContext()).size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                // 添加
                LayoutInflater inflater = LayoutInflater.from(getContext());
                RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout
                                .lay_face_content,
                        container, false);

                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));

                // 设置Adapter
                List<Face.Bean> faces = Face.all(getContext()).get(position).faces;
                FaceAdapter adapter = new FaceAdapter(faces, new RecyclerAdapter
                        .AdapterListenerImpl<Face.Bean>() {

                    @Override
                    public void onItemClick(RecyclerAdapter.ViewHolder holder, Face.Bean bean) {
                        if (mCallback == null)
                            return;
                        // 表情添加到输入框
                        EditText editText = mCallback.getInputEditText();
                        Face.inputFace(getContext(), editText.getText(), bean,
                                (int) (editText.getTextSize() + Ui.dipToPx(getResources(), 2)));

                    }
                });
                recyclerView.setAdapter(adapter);
                // 添加
                container.addView(recyclerView);
                return recyclerView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object
                    object) {
                // 移除
                container.removeView((View) object);
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                // 拿到表情盘的描述
                return Face.all(getContext()).get(position).name;
            }
        });

    }

    // 初始化语音
    private void initRecord(View root) {
        View recordView = mRecordPanel = root.findViewById(R.id.lay_panel_record);

        final AudioRecordView audioRecordView = recordView.findViewById(R.id.view_audio_record);

        // 录音的缓存文件
        File tmpFile = Application.getAudioTmpFile(true);
        // 录音辅助工具类
        final AudioRecordHelper helper = new AudioRecordHelper(tmpFile, new AudioRecordHelper
                .RecordCallback() {
            @Override
            public void onRecordStart() {
                //...
            }

            @Override
            public void onProgress(long time) {
                //...
            }

            @Override
            public void onRecordDone(File file, long time) {
                // 时间是毫秒，小于1秒则不发送
                if (time < 1000) {
                    return;
                }

                // 更改为一个发送的录音文件
                File audioFile = Application.getAudioTmpFile(false);
                if (file.renameTo(audioFile)) {
                    // 通知到聊天界面
                    PanelCallback panelCallback = mCallback;
                    if (panelCallback != null) {
                        panelCallback.onRecordDone(audioFile, time);
                    }
                }
            }
        });

        // 初始化
        audioRecordView.setup(new AudioRecordView.Callback() {
            @Override
            public void requestStartRecord() {
                // 请求开始
                helper.recordAsync();
            }

            @Override
            public void requestStopRecord(int type) {
                switch (type){
                    case AudioRecordView.END_TYPE_CANCEL:
                    case AudioRecordView.END_TYPE_DELETE:
                        // 删除和取消都代表想要取消
                        helper.stop(true);
                        break;
                    case AudioRecordView.END_TYPE_NONE:
                    case AudioRecordView.END_TYPE_PLAY:
                        // 播放暂时当中就是想要发送
                        helper.stop(false);
                        break;
                }
            }
        });
    }

    // 初始化图片
    private void initGallery(View root) {
        final View galleryPanel = mGalleryPanel = root.findViewById(R.id.lay_gallery_panel);
        final GalleryView galleryView = galleryPanel.findViewById(R.id.view_gallery);
        final TextView selectedSize = galleryPanel.findViewById(R.id.txt_gallery_select_count);

        galleryView.setup(getLoaderManager(), new GalleryView.SelectedChangeListener() {
            @Override
            public void onSelectedCountChanged(int count) {
                String resStr = getText(R.string.label_gallery_selected_size).toString();
                selectedSize.setText(String.format(resStr, count));
            }
        });

        galleryPanel.findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGalleySendClick(galleryView, galleryView.getSelectedPath());
            }
        });

    }

    // 点击的时候触发，传回一个控件和选中的路径
    private void onGalleySendClick(GalleryView galleryView, String[] paths) {
        // 通知给聊天界面
        // 清理状态
        galleryView.clear();

        // 删除逻辑
        PanelCallback callback = mCallback;
        if (callback == null)
            return;

        callback.onSendGallery(paths);

    }

    public void showFace() {
        mRecordPanel.setVisibility(View.GONE);
        mGalleryPanel.setVisibility(View.GONE);
        mFacePanel.setVisibility(View.VISIBLE);
    }

    public void showRecord() {
        mRecordPanel.setVisibility(View.VISIBLE);
        mGalleryPanel.setVisibility(View.GONE);
        mFacePanel.setVisibility(View.GONE);
    }

    public void showGallery() {
        mRecordPanel.setVisibility(View.GONE);
        mGalleryPanel.setVisibility(View.VISIBLE);
        mFacePanel.setVisibility(View.GONE);
    }

    // 回调聊天界面的Callback
    public interface PanelCallback {
        EditText getInputEditText();

        // 返回需要发送的图片
        void onSendGallery(String[] paths);

        // 返回录音文件和时常
        void onRecordDone(File file, long time);
    }

}
