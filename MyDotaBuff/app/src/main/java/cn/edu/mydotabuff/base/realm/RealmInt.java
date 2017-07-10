package cn.edu.mydotabuff.base.realm;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by sadhu on 2017/7/7.
 * 描述
 */
public class RealmInt extends RealmObject implements Parcelable {
    public int val;
    public RealmInt() {
    }

    public RealmInt(int val) {
        this.val = val;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.val);
    }

    protected RealmInt(Parcel in) {
        this.val = in.readInt();
    }

    public static final Parcelable.Creator<RealmInt> CREATOR = new Parcelable.Creator<RealmInt>() {
        @Override
        public RealmInt createFromParcel(Parcel source) {
            return new RealmInt(source);
        }

        @Override
        public RealmInt[] newArray(int size) {
            return new RealmInt[size];
        }
    };
}
