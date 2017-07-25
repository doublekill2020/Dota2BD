package cn.edu.mydotabuff.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Fitz
 * @version V1.0
 * @Description: 新闻实体类
 * @email FitzPro@qq.com
 * @date 2017/7/25 9:15
 */
public class NewsBean implements Parcelable {
    private String pic;
    private String title;
    private String content;
    private String time;
    private String url;
    private String isVideo;

    public String getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(String isVideo) {
        this.isVideo = isVideo;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public NewsBean(String pic, String title, String content, String time,
                    String url, String isVideo) {
        super();
        this.pic = pic;
        this.title = title;
        this.content = content;
        this.time = time;
        this.url = url;
        this.isVideo = isVideo;
    }

    @Override
    public String toString() {
        return "NewsBean [pic=" + pic + ", title=" + title + ", content="
                + content + ", time=" + time + ", url=" + url + ", isVideo="
                + isVideo + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pic);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.time);
        dest.writeString(this.url);
        dest.writeString(this.isVideo);
    }

    protected NewsBean(Parcel in) {
        this.pic = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.time = in.readString();
        this.url = in.readString();
        this.isVideo = in.readString();
    }

    public static final Creator<NewsBean> CREATOR = new Creator<NewsBean>() {
        @Override
        public NewsBean createFromParcel(Parcel source) {
            return new NewsBean(source);
        }

        @Override
        public NewsBean[] newArray(int size) {
            return new NewsBean[size];
        }
    };
}
