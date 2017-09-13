package cn.edu.mydotabuff.ui.presenter;

import cn.edu.mydotabuff.model.MatchDetail;

/**
 * Created by sadhu on 2017/7/5.
 * 描述:
 */
public interface IMatchDetaiPresenter extends cn.edu.mydotabuff.base.IBasePresenter {
    void fetchMatchDetailInfo(String matchId);
}
