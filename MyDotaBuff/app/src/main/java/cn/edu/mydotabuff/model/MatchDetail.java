package cn.edu.mydotabuff.model;

import cn.edu.mydotabuff.base.realm.RealmInt;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sadhu on 2017/7/7.
 * 描述
 */
public class MatchDetail extends RealmObject {
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
    public int lobby_type; // 游戏类型

    public RealmList<MatchPlayInfo> players;
    public RealmList<RealmInt> radiant_gold_adv;
    public RealmList<RealmInt> radiant_xp_adv;

}
