package cn.edu.mydotabuff.model;

import io.realm.RealmObject;

/**
 * Created by sadhu on 2017/7/7.
 * 描述
 */
public class BenchmarksBean extends RealmObject {
    /**
     * gold_per_min : {"raw":433,"pct":0.958967266021208}
     * xp_per_min : {"raw":571,"pct":0.9741816505301982}
     * kills_per_min : {"raw":0.09437672040896579,"pct":0.5984324573536192}
     * last_hits_per_min : {"raw":3.869445536767597,"pct":0.9930843706777317}
     * hero_damage_per_min : {"raw":558.2618953991348,"pct":0.991701244813278}
     * hero_healing_per_min : {"raw":17.176563114431776,"pct":0.921161825726141}
     * tower_damage : {"raw":65,"pct":0.30567081604426005}
     */

    public BenchmarksMinBean gold_per_min;
    public BenchmarksMinBean xp_per_min;
    public BenchmarksMinBean kills_per_min;
    public BenchmarksMinBean last_hits_per_min;
    public BenchmarksMinBean hero_damage_per_min;
    public BenchmarksMinBean hero_healing_per_min;
    public BenchmarksMinBean tower_damage;
}
