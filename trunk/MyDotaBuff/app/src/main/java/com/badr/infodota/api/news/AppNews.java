package com.badr.infodota.api.news;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 21.04.14
 * Time: 18:27
 */
public class AppNews {
    private Long appid;
    private List<NewsItem> newsitems;

    public AppNews() {
    }

    public Long getAppid() {
        return appid;
    }

    public void setAppid(Long appid) {
        this.appid = appid;
    }

    public List<NewsItem> getNewsitems() {
        return newsitems;
    }

    public void setNewsitems(List<NewsItem> newsitems) {
        this.newsitems = newsitems;
    }
}
