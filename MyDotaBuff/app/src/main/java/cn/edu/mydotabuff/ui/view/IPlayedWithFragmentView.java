package cn.edu.mydotabuff.ui.view;

import java.util.List;

import cn.edu.mydotabuff.base.IBaseView;
import cn.edu.mydotabuff.model.Match;
import cn.edu.mydotabuff.model.PlayedWithWrapper;

public interface IPlayedWithFragmentView extends IBaseView {
    void notifyDataUpdate(List<PlayedWithWrapper> data);
    void setRefreshCompleted();
}
