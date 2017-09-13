package cn.edu.mydotabuff.ui.presenter;

import cn.edu.mydotabuff.base.IBasePresenter;
import cn.edu.mydotabuff.model.PlayerInfo;

/**
 * Created by nevermore on 2017/7/10 0010.
 */

public interface IPlayerDetailPresenter extends IBasePresenter {

    void follow();

    PlayerInfo queryPlayerInfo(String accountId);
}
