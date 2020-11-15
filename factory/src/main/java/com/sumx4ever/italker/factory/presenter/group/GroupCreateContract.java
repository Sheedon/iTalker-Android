package com.sumx4ever.italker.factory.presenter.group;


import com.sumx4ever.factory.model.Author;
import com.sumx4ever.factory.presenter.BaseContract;

/**
 * 群创建的契约
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public interface GroupCreateContract {
    interface Presenter extends BaseContract.Presenter {
        // 创建
        void create(String name, String desc, String picture);

        // 更改一个Model的选中状态
        void changeSelect(ViewModel model, boolean isSelected);
    }

    interface View extends BaseContract.RecyclerView<Presenter, ViewModel> {
        // 创建成功
        void onCreateSucceed();
    }

    class ViewModel {
        // 用户信息
        public Author author;
        // 是否选中
        public boolean isSelected;
    }

}
