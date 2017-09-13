package cn.edu.mydotabuff.ui.view;

import java.util.List;

import cn.edu.mydotabuff.base.IBaseView;
import cn.edu.mydotabuff.model.Match;

/**
 * Created by nevermore on 2017/6/28 0028.
 */

public interface IRecentMatchView extends IBaseView {

    void setDataToRecycleView(List<Match> matches);
    void notifyDataUpdate();
}
