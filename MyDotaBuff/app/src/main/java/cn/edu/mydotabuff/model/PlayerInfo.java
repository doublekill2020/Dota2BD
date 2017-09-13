package cn.edu.mydotabuff.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nevermore on 2017/6/28 0028.
 */

public class PlayerInfo extends RealmObject implements Serializable{

    @PrimaryKey
    public String account_id;
    public boolean follow = false;
    //单排积分
    public String solo_competitive_rank;
    //最近普通匹配的积分
    public String competitive_rank;

    public Profile profile;

    public PlayerWL playerWL;

}
