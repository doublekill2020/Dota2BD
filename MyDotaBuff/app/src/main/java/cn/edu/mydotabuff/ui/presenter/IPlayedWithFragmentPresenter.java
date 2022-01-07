package cn.edu.mydotabuff.ui.presenter;

import java.util.List;
import java.util.Map;

import cn.edu.mydotabuff.base.IBasePresenter;
import cn.edu.mydotabuff.model.PlayerInfo;

public interface IPlayedWithFragmentPresenter extends IBasePresenter {

    void getData(String accountId);
}
