package cn.edu.mydotabuff.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nevermore on 2017/7/24 0024.
 */

public class PlayerWL extends RealmObject {
    @PrimaryKey
    public String accountId;
    public int win;
    public int lose;
    public float winRate;
}
