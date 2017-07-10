package cn.edu.mydotabuff.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import cn.edu.mydotabuff.base.realm.RealmInt;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by sadhu on 2017/7/7.
 * 描述
 */
public class MatchPlayInfo extends RealmObject implements Parcelable {

    public String match_id; // 比赛id
    public String account_id; // 用户id
    public int life_state_dead;// 死亡时间
    public int level; // 等级
    public int kills; // 击杀
    public int assists; // 助攻
    public int deaths; // 死亡
    public int kda; // 死亡
    public int total_gold; // 总金钱
    public int total_xp; // 总经验
    public int denies; // 反补
    // 物品栏
    private int item_0;
    private int item_1;
    private int item_2;
    private int item_3;
    private int item_4;
    private int item_5;
    public float teamfight_participation; // 参战率
    public double stuns; // 晕眩英雄/控制时间
    public int camps_stacked; // 堆积野怪次数
    public int rune_pickups; // 拾取符文次数
    public int towers_killed; // 推塔数
    public int neutral_kills; // 中立生物击杀
    public int courier_kills; // 信使击杀
    public int lane_kills; // 小兵击杀

    public RealmList<RealmInt> xp_t; // 经验曲线
    public RealmList<RealmInt> lh_t; // 正补曲线
    public RealmList<RealmInt> gold_t; // 经济曲线
    public BenchmarksBean benchmarks;// 比赛评估

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.match_id);
        dest.writeString(this.account_id);
        dest.writeInt(this.life_state_dead);
        dest.writeInt(this.level);
        dest.writeInt(this.kills);
        dest.writeInt(this.assists);
        dest.writeInt(this.deaths);
        dest.writeInt(this.kda);
        dest.writeInt(this.total_gold);
        dest.writeInt(this.total_xp);
        dest.writeInt(this.denies);
        dest.writeInt(this.item_0);
        dest.writeInt(this.item_1);
        dest.writeInt(this.item_2);
        dest.writeInt(this.item_3);
        dest.writeInt(this.item_4);
        dest.writeInt(this.item_5);
        dest.writeFloat(this.teamfight_participation);
        dest.writeDouble(this.stuns);
        dest.writeInt(this.camps_stacked);
        dest.writeInt(this.rune_pickups);
        dest.writeInt(this.towers_killed);
        dest.writeInt(this.neutral_kills);
        dest.writeInt(this.courier_kills);
        dest.writeInt(this.lane_kills);
        dest.writeList(this.xp_t);
        dest.writeList(this.lh_t);
        dest.writeList(this.gold_t);
        dest.writeParcelable(this.benchmarks, flags);
    }

    public MatchPlayInfo() {
    }

    protected MatchPlayInfo(Parcel in) {
        this.match_id = in.readString();
        this.account_id = in.readString();
        this.life_state_dead = in.readInt();
        this.level = in.readInt();
        this.kills = in.readInt();
        this.assists = in.readInt();
        this.deaths = in.readInt();
        this.kda = in.readInt();
        this.total_gold = in.readInt();
        this.total_xp = in.readInt();
        this.denies = in.readInt();
        this.item_0 = in.readInt();
        this.item_1 = in.readInt();
        this.item_2 = in.readInt();
        this.item_3 = in.readInt();
        this.item_4 = in.readInt();
        this.item_5 = in.readInt();
        this.teamfight_participation = in.readFloat();
        this.stuns = in.readDouble();
        this.camps_stacked = in.readInt();
        this.rune_pickups = in.readInt();
        this.towers_killed = in.readInt();
        this.neutral_kills = in.readInt();
        this.courier_kills = in.readInt();
        this.lane_kills = in.readInt();

        this.xp_t = new RealmList<>();
        this.xp_t.addAll(in.createTypedArrayList(RealmInt.CREATOR));
        this.lh_t = new RealmList<>();
        this.lh_t.addAll(in.createTypedArrayList(RealmInt.CREATOR));
        this.gold_t = new RealmList<>();
        this.gold_t.addAll(in.createTypedArrayList(RealmInt.CREATOR));
        this.benchmarks = in.readParcelable(BenchmarksBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MatchPlayInfo> CREATOR = new Parcelable.Creator<MatchPlayInfo>() {
        @Override
        public MatchPlayInfo createFromParcel(Parcel source) {
            return new MatchPlayInfo(source);
        }

        @Override
        public MatchPlayInfo[] newArray(int size) {
            return new MatchPlayInfo[size];
        }
    };
}
