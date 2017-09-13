package cn.edu.mydotabuff.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by sadhu on 2017/7/7.
 * 描述
 */
public class BenchmarksMinBean extends RealmObject implements Parcelable {
    /**
     * raw : 433
     * pct : 0.958967266021208
     */
    public float raw;
    public double pct;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.raw);
        dest.writeDouble(this.pct);
    }

    public BenchmarksMinBean() {
    }

    protected BenchmarksMinBean(Parcel in) {
        this.raw = in.readFloat();
        this.pct = in.readDouble();
    }

    public static final Parcelable.Creator<BenchmarksMinBean> CREATOR = new Parcelable.Creator<BenchmarksMinBean>() {
        @Override
        public BenchmarksMinBean createFromParcel(Parcel source) {
            return new BenchmarksMinBean(source);
        }

        @Override
        public BenchmarksMinBean[] newArray(int size) {
            return new BenchmarksMinBean[size];
        }
    };
}
