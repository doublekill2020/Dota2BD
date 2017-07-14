package cn.edu.mydotabuff.model;

import io.realm.RealmObject;

/**
 * Created by nevermore on 2017/7/12 0012.
 */

public class Hero extends RealmObject {
    public String hero_id;
    public long last_played;
    public int games;
    public int win;

}
