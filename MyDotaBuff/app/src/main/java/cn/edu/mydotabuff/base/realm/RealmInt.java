package cn.edu.mydotabuff.base.realm;

import io.realm.RealmObject;

/**
 * Created by sadhu on 2017/7/7.
 * 描述
 */
public class RealmInt extends RealmObject {
    public int val;
    public RealmInt() {
    }

    public RealmInt(int val) {
        this.val = val;
    }
}
