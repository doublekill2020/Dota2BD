package com.badr.infodota.api.news;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 21.04.14
 * Time: 18:24
 */
public class NewsItem implements Serializable, Comparable {
    private String gid;
    private String title;
    private String url;
    //html
    private String contents;
    //author
    private String author;
    //if author is empty author
    private String feedlabel;
    //sort by edate
    private Long date;

    public NewsItem() {
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFeedlabel() {
        return feedlabel;
    }

    public void setFeedlabel(String feedlabel) {
        this.feedlabel = feedlabel;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsItem)) return false;

        NewsItem newsItem = (NewsItem) o;

        if (date != null ? !date.equals(newsItem.date) : newsItem.date != null) return false;
        if (gid != null ? !gid.equals(newsItem.gid) : newsItem.gid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gid != null ? gid.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object another) {
        if (another instanceof NewsItem) {
            //newer news must be before the older one
            return ((NewsItem) another).getDate().compareTo(date);
        }
        return -1;
    }
}
