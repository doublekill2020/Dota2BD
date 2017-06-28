package cn.edu.mydotabuff.ui.presenter;

/**
 * Created by sadhu on 2017/6/27.
 * 描述: 登入界面presenter接口
 */
public interface ILoginPresenter extends IBasePresenter {

    void searchPlayer(String key, boolean isExactSearch);

    void bindPlayer(long accountId);
}
