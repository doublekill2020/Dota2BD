package cn.edu.mydotabuff.model;

import android.os.Parcel;
import android.os.Parcelable;

import cn.edu.mydotabuff.base.realm.RealmInt;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sadhu on 2017/7/7.
 * 描述
 */
public class MatchDetail extends RealmObject implements Parcelable {
    @PrimaryKey
    public String match_id;
    public int barracks_status_radiant; // 天辉兵营状态
    public int barracks_status_dire; // 夜魇兵营状态
    public int tower_status_radiant; // 夜魇防御塔状态
    public int tower_status_dire; // 天辉防御塔状态
    public int radiant_score; // 天辉击杀数量
    public int dire_score; // 夜魇击杀数量
    public int patch; // 补丁版本
    public int region; // 区域
    public int duration; // 时长
    public long start_time; // 开始时间
    public int first_blood_time; // 一血时间
    public int lobby_type; // 游戏level
    public int game_mode; // 比赛模式
    public boolean radiant_win; // 天辉获胜

    public RealmList<MatchPlayInfo> players;
    public RealmList<RealmInt> radiant_gold_adv;
    public RealmList<RealmInt> radiant_xp_adv;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.match_id);
        dest.writeInt(this.barracks_status_radiant);
        dest.writeInt(this.barracks_status_dire);
        dest.writeInt(this.tower_status_radiant);
        dest.writeInt(this.tower_status_dire);
        dest.writeInt(this.radiant_score);
        dest.writeInt(this.dire_score);
        dest.writeInt(this.patch);
        dest.writeInt(this.region);
        dest.writeInt(this.duration);
        dest.writeLong(this.start_time);
        dest.writeInt(this.first_blood_time);
        dest.writeInt(this.lobby_type);
        dest.writeInt(this.game_mode);
        dest.writeByte((byte) (this.radiant_win ? 1 : 0));
        dest.writeList(this.players);
        dest.writeList(this.radiant_gold_adv);
        dest.writeList(this.radiant_xp_adv);
    }

    public MatchDetail() {
    }

    protected MatchDetail(Parcel in) {
        this.match_id = in.readString();
        this.barracks_status_radiant = in.readInt();
        this.barracks_status_dire = in.readInt();
        this.tower_status_radiant = in.readInt();
        this.tower_status_dire = in.readInt();
        this.radiant_score = in.readInt();
        this.dire_score = in.readInt();
        this.patch = in.readInt();
        this.region = in.readInt();
        this.duration = in.readInt();
        this.start_time = in.readLong();
        this.first_blood_time = in.readInt();
        this.lobby_type = in.readInt();
        this.game_mode = in.readInt();
        this.radiant_win = in.readByte() == 1;
        this.players = new RealmList<>();
        this.players.addAll(in.createTypedArrayList(MatchPlayInfo.CREATOR));
        this.radiant_gold_adv = new RealmList<>();
        this.radiant_gold_adv.addAll(in.createTypedArrayList(RealmInt.CREATOR));
        this.radiant_xp_adv = new RealmList<>();
        this.radiant_xp_adv.addAll(in.createTypedArrayList(RealmInt.CREATOR));
    }

    public static final Parcelable.Creator<MatchDetail> CREATOR = new Parcelable.Creator<MatchDetail>() {
        @Override
        public MatchDetail createFromParcel(Parcel source) {
            return new MatchDetail(source);
        }

        @Override
        public MatchDetail[] newArray(int size) {
            return new MatchDetail[size];
        }
    };
}
