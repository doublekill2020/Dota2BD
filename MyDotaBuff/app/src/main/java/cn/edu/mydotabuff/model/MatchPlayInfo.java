package cn.edu.mydotabuff.model;

import cn.edu.mydotabuff.base.realm.RealmInt;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by sadhu on 2017/7/7.
 * 描述
 */
public class MatchPlayInfo extends RealmObject {

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

}
