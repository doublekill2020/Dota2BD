package com.badr.infodota.service.news;

import android.content.Context;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.api.news.AppNews;
import com.badr.infodota.api.news.AppNewsResult;

/**
 * User: Histler
 * Date: 21.04.14
 */
public class NewsServiceImpl implements NewsService {

    @Override
    public AppNews getNews(Context context, Long fromDate) {
        AppNewsResult result = BeanContainer.getInstance().getSteamService().getNews(fromDate);
        if(result!=null){
            return result.getAppnews();
        }
        return null;
    }
}
