package cn.edu.mydotabuff.presenter;

/**
 * Created by sadhu on 2017/6/27.
 * 描述: 登入界面presenter接口
 */
public interface ILoginPresenter {
    void searchPlayer(String key);

    void searchPlayer(String key, boolean isExactSearch);

    void bindPlayer(long accountId);
}
