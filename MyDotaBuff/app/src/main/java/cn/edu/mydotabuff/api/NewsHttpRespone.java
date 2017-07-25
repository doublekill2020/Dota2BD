package cn.edu.mydotabuff.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import cn.edu.mydotabuff.model.NewsBean;

/**
 * @author Fitz
 * @version V1.0
 * @Description: 新闻返回实体类
 * @email FitzPro@qq.com
 * @date 2017/7/25 13:47
 */
public class NewsHttpRespone {

    private ArrayList<NewsBean> data;
    @SerializedName("total_pages")
    private int totalPages;

    public ArrayList<NewsBean> getData() {
        return data;
    }

    public void setData(ArrayList<NewsBean> data) {
        this.data = data;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
