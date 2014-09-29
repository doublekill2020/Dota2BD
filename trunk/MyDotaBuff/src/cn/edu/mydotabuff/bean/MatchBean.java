package cn.edu.mydotabuff.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class MatchBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private String matchId;
	private String startTime;
	private int lobbyType;
	private int radiantId;
	private int direId;
	private ArrayList<PlayerBean> players;

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public int getLobbyType() {
		return lobbyType;
	}

	public void setLobbyType(int lobbyType) {
		this.lobbyType = lobbyType;
	}

	public int getRadiantId() {
		return radiantId;
	}

	public void setRadiantId(int radiantId) {
		this.radiantId = radiantId;
	}

	public int getDireId() {
		return direId;
	}

	public void setDireId(int direId) {
		this.direId = direId;
	}

	public ArrayList<PlayerBean> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<PlayerBean> players) {
		this.players = players;
	}

	public MatchBean(String matchId, String startTime, int lobbyType,
			int radiantId, int direId, ArrayList<PlayerBean> players) {
		super();
		this.matchId = matchId;
		this.startTime = startTime;
		this.lobbyType = lobbyType;
		this.radiantId = radiantId;
		this.direId = direId;
		this.players = players;
	}

}
