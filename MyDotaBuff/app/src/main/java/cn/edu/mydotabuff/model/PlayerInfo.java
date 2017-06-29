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
}
