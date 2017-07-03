package cn.edu.mydotabuff.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tinker on 2017/7/3.
 */

public class Profile extends RealmObject {

    @PrimaryKey
    public String account_id;
    public String personaname;
    public String name;
    public int cheese;
    public String steamid;
    public String avatar;
    public String avatarmedium;
    public String avatarfull;
    public String profileurl;
    public String last_login;
    public String loccountrycode;
}
