package cn.edu.mydotabuff.model;

import java.io.Serializable;

public class PlayerBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accountId;
	private int playerSlot;
	private int heroId;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public int getPlayerSlot() {
		return playerSlot;
	}

	public void setPlayerSlot(int playerSlot) {
		this.playerSlot = playerSlot;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public PlayerBean(String accountId, int playerSlot, int heroId) {
		super();
		this.accountId = accountId;
		this.playerSlot = playerSlot;
		this.heroId = heroId;
	}

}
