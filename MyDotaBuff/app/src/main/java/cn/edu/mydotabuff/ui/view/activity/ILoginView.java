package cn.edu.mydotabuff.ui.view.activity;

import java.util.List;

import cn.edu.mydotabuff.model.PlayerInfo;

/**
 * Created by sadhu on 2017/6/28.
 * 描述:
 */
public interface ILoginView extends IBaseView {
    void showResult(List<PlayerInfo> playerInfos);
}
