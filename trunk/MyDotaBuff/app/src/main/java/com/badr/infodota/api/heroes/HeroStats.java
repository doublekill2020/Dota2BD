package com.badr.infodota.api.heroes;

import com.badr.infodota.util.HasId;

/**
 * User: ABadretdinov
 * Date: 29.08.13
 * Time: 15:12
 */
public class HeroStats implements HasId {

    private long id;
    private int Alignment;
    private String Patch;
    private int Movespeed;
    private int MaxDmg;
    private int MinDmg;
    private int HP;
    private int Mana;
    private float HPRegen;
    private float ManaRegen;
    private float Armor;
    private int Range;
    private int ProjectileSpeed;
    private int BaseStr;
    private int BaseAgi;
    private int BaseInt;
    private float StrGain;
    private float AgiGain;
    private float IntGain;
    /*
    * 0-str
	* 1-agi
	* 2-int
	* */
    private int PrimaryStat;
    private float BaseAttackTime;
    private int DayVision;
    private int NightVision;
    private float AttackPoint;
    private float AttackSwing;
    private float CastPoint;
    private float CastSwing;
    private float Turnrate;
    //Who needs legs, when you got wings?
    private int Legs;
    private String[] roles;

    public String getPatch() {
        return Patch;
    }

    public void setPatch(String patch) {
        Patch = patch;
    }

    public int getAlignment() {
        return Alignment;
    }

    public void setAlignment(int alignment) {
        Alignment = alignment;
    }

    public int getMovespeed() {
        return Movespeed;
    }

    public void setMovespeed(int movespeed) {
        Movespeed = movespeed;
    }

    public int getMaxDmg() {
        return MaxDmg;
    }

    public void setMaxDmg(int maxDmg) {
        MaxDmg = maxDmg;
    }

    public int getMinDmg() {
        return MinDmg;
    }

    public void setMinDmg(int minDmg) {
        MinDmg = minDmg;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMana() {
        return Mana;
    }

    public void setMana(int mana) {
        Mana = mana;
    }

    public float getHPRegen() {
        return HPRegen;
    }

    public void setHPRegen(float HPRegen) {
        this.HPRegen = HPRegen;
    }

    public float getManaRegen() {
        return ManaRegen;
    }

    public void setManaRegen(float manaRegen) {
        ManaRegen = manaRegen;
    }

    public float getArmor() {
        return Armor;
    }

    public void setArmor(float armor) {
        Armor = armor;
    }

    public int getRange() {
        return Range;
    }

    public void setRange(int range) {
        Range = range;
    }

    public int getProjectileSpeed() {
        return ProjectileSpeed;
    }

    public void setProjectileSpeed(int projectileSpeed) {
        ProjectileSpeed = projectileSpeed;
    }

    public int getBaseStr() {
        return BaseStr;
    }

    public void setBaseStr(int baseStr) {
        BaseStr = baseStr;
    }

    public int getBaseAgi() {
        return BaseAgi;
    }

    public void setBaseAgi(int baseAgi) {
        BaseAgi = baseAgi;
    }

    public int getBaseInt() {
        return BaseInt;
    }

    public void setBaseInt(int baseInt) {
        BaseInt = baseInt;
    }

    public float getStrGain() {
        return StrGain;
    }

    public void setStrGain(float strGain) {
        StrGain = strGain;
    }

    public float getAgiGain() {
        return AgiGain;
    }

    public void setAgiGain(float agiGain) {
        AgiGain = agiGain;
    }

    public float getIntGain() {
        return IntGain;
    }

    public void setIntGain(float intGain) {
        IntGain = intGain;
    }

    public int getPrimaryStat() {
        return PrimaryStat;
    }

    public void setPrimaryStat(int primaryStat) {
        PrimaryStat = primaryStat;
    }

    public float getBaseAttackTime() {
        return BaseAttackTime;
    }

    public void setBaseAttackTime(float baseAttackTime) {
        BaseAttackTime = baseAttackTime;
    }

    public int getDayVision() {
        return DayVision;
    }

    public void setDayVision(int dayVision) {
        DayVision = dayVision;
    }

    public int getNightVision() {
        return NightVision;
    }

    public void setNightVision(int nightVision) {
        NightVision = nightVision;
    }

    public float getAttackPoint() {
        return AttackPoint;
    }

    public void setAttackPoint(float attackPoint) {
        AttackPoint = attackPoint;
    }

    public float getAttackSwing() {
        return AttackSwing;
    }

    public void setAttackSwing(float attackSwing) {
        AttackSwing = attackSwing;
    }

    public float getCastPoint() {
        return CastPoint;
    }

    public void setCastPoint(float castPoint) {
        CastPoint = castPoint;
    }

    public float getCastSwing() {
        return CastSwing;
    }

    public void setCastSwing(float castSwing) {
        CastSwing = castSwing;
    }

    public float getTurnrate() {
        return Turnrate;
    }

    public void setTurnrate(float turnrate) {
        Turnrate = turnrate;
    }

    public int getLegs() {
        return Legs;
    }

    public void setLegs(int legs) {
        Legs = legs;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
