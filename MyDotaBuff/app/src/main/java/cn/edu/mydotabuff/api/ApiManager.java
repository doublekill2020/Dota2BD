package cn.edu.mydotabuff.api;

import cn.edu.mydotabuff.base.OpenDotaApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author Fitz
 * @version V1.0
 * @Description: Api接口
 * @email FitzPro@qq.com
 * @date 2017/7/25 13:41
 */
public class ApiManager {

    private volatile static ApiManager manager;
    private OpenDotaApi.Dota2NewsService dota2NewsService;

    private ApiManager() {
        dota2NewsService = OpenDotaApi.getInstance().getDota2NewsService();
    }

    public static ApiManager getManager() {
        if (manager == null) {
            synchronized (ApiManager.class) {
                if (manager == null) {
                    manager = new ApiManager();
                }
            }
        }
        return manager;
    }


    public void requestHotNews(int page, Action1<NewsHttpRespone> responeAction, Action1<Throwable> throwableAction) {
        dota2NewsService.getHotNews(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responeAction, throwableAction);

    }

    public void requestGovNews(int page, Action1<NewsHttpRespone> responeAction, Action1<Throwable> throwableAction) {
        dota2NewsService.getGovNews(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responeAction, throwableAction);

    }

    public void requestMatchNews(int page, Action1<NewsHttpRespone> responeAction, Action1<Throwable> throwableAction) {
        dota2NewsService.getMatchNews(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responeAction, throwableAction);

    }

    public void requestVerNews(int page, Action1<NewsHttpRespone> responeAction, Action1<Throwable> throwableAction) {
        dota2NewsService.getVerNews(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responeAction, throwableAction);

    }
}
