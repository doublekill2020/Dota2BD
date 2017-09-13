package cn.edu.mydotabuff.ui.view.activity;

import cn.edu.mydotabuff.base.IBaseView;
import cn.edu.mydotabuff.model.MatchDetail;

/**
 * Created by sadhu on 2017/7/5.
 * 描述: 比赛详情
 */
public interface IMatchDetailView extends IBaseView {
    void fetchMatchDetailInfoSuccess(MatchDetail matchDetail);
}
