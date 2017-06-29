package cn.edu.mydotabuff.ui.presenter;

import java.util.List;

import cn.edu.mydotabuff.base.IBasePresenter;

/**
 * Created by nevermore on 2017/6/28 0028.
 */

public interface IFollowFragmentPresenter extends IBasePresenter {

    void getDataFromDb(List<String> followers);

    void doSync(List<String> followers);
}
