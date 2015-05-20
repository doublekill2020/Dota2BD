package com.badr.infodota.service.news;

import android.content.Context;

import com.badr.infodota.api.news.AppNews;

/**
 * User: Histler
 * Date: 21.04.14
 */
public interface NewsService{
    AppNews getNews(Context context, Long fromDate);
}
