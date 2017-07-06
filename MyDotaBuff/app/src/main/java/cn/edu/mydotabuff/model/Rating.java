package cn.edu.mydotabuff.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tinker on 2017/7/6.
 * 天梯分变化
 */

public class Rating extends RealmObject {
    @PrimaryKey
    public String id;
    public String account_id;
    public String match_id;
    public String solo_competitive_rank;
    public String competitive_rank;
    public Date time;
}
