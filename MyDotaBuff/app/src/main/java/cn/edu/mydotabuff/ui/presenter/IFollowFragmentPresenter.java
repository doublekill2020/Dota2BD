package cn.edu.mydotabuff.ui.presenter;

import java.util.List;
import java.util.Map;

import cn.edu.mydotabuff.base.IBasePresenter;
import cn.edu.mydotabuff.model.PlayerInfo;

/**
 * Created by nevermore on 2017/6/28 0028.
 */

public interface IFollowFragmentPresenter extends IBasePresenter {

    void getDataFromDb(List<String> followers);

    void doSync(List<String> followers);

    List<String> getAllFollowers();

    Map<String, PlayerInfo> getPlayerInfoMap();

}
