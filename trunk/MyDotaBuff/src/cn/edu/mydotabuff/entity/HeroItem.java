/**
 * 
 */
package cn.edu.mydotabuff.entity;

/**
 * 英雄实体
 * 
 * @author tupunco
 */
public class HeroItem {
    public String keyName;
    /**
     * 属性类型
     */
    public String hp;
    /**
     * 阵容
     */
    public String faction;
    /**
     * 英雄名称
     */
    public String name;
    public String name_l;
    /**
     * 攻击类型
     */
    public String atk;
    public String atk_l;
    /**
     * 角色定位
     */
    public String[] roles;
    public String[] roles_l;

    /**
     * 昵称/别称
     * 
     * FROM:http://dota2.replays.net/
     */
    public String[] nickname_l;
    /**
     * 本英雄统计参数信息
     */
    public HeroStatsItem statsall;

    /**
     * 是否已经收藏
     * -1 未加载, 0 否, 1 是
     */
    public int hasFavorite = -1;

    @Override
    public String toString() {
        return String.format(
                "[HeroItem keyName:%s,name:%s,name_l:%s,hp:%s,faction:%s,atk:%s,roles:%s]",
                keyName, name, name_l, hp, faction, atk_l, roles_l);
    }
}