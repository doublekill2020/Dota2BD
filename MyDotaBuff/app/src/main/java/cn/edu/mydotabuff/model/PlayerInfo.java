package cn.edu.mydotabuff.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nevermore on 2017/6/28 0028.
 */

public class PlayerInfo extends RealmObject {

    @PrimaryKey
    public String account_id;
    public boolean follow = false;
    //单排积分
    public String solo_competitive_rank;
    //最近普通匹配的积分
    public String competitive_rank;

    public Profile profile;

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public String getSolo_competitive_rank() {
        return solo_competitive_rank;
    }

    public void setSolo_competitive_rank(String solo_competitive_rank) {
        this.solo_competitive_rank = solo_competitive_rank;
    }

    public String getCompetitive_rank() {
        return competitive_rank;
    }

    public void setCompetitive_rank(String competitive_rank) {
        this.competitive_rank = competitive_rank;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
