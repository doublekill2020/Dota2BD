package cn.edu.mydotabuff.common.bean;
import java.io.Serializable;

public class AbilityBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	private int ability;
	private int time;
	private int level;
	public AbilityBean(int ability, int time, int level) {
		super();
		this.ability = ability;
		this.time = time;
		this.level = level;
	}
	public int getAbility() {
		return ability;
	}
	public void setAbility(int ability) {
		this.ability = ability;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
}
