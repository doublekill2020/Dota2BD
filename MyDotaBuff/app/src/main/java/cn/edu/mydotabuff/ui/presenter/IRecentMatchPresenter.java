package cn.edu.mydotabuff.ui.presenter;

import java.util.List;
import java.util.Map;

import cn.edu.mydotabuff.base.IBasePresenter;
import cn.edu.mydotabuff.model.PlayerInfo;

/**
 * Created by tinker on 2017/7/24.
 */

public interface IRecentMatchPresenter extends IBasePresenter {
    void getDataFromDb(List<String> followers);

    void doSync(List<String> followers);

    List<String> getAllFollowers();

    Map<String, PlayerInfo> getPlayerInfoMap();

    void doSyncPlayersRating(List<String> playersAccountIds);
}
