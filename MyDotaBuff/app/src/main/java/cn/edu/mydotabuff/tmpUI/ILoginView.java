package cn.edu.mydotabuff.tmpUI;

import java.util.List;

import cn.edu.mydotabuff.model.SearchPlayerResult;

/**
 * Created by sadhu on 2017/6/28.
 * 描述:
 */
public interface ILoginView extends IBaseView {
    void showResult(List<SearchPlayerResult> searchPlayerResults);
}
