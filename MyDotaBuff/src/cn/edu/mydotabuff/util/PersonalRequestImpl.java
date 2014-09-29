package cn.edu.mydotabuff.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import cn.edu.mydotabuff.APIConstants;
import cn.edu.mydotabuff.base.BaseRequestInterface;
import cn.edu.mydotabuff.http.HttpRequestImpl;
import cn.edu.mydotabuff.http.IInfoReceive;

public class PersonalRequestImpl extends BaseRequestInterface {

	public PersonalRequestImpl(IInfoReceive iInfoReceive) {
		super(iInfoReceive);
	}

	public void getMatchHistory(String ID, String startAt) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", APIConstants.API_KEY);
		params.put("account_id", ID);
		params.put("matches_requested", "11");
		params.put("start_at_match_id", startAt);
		request(APIConstants.GET_MATCH_HISTORY, params, true);
	}

	public void getHeroList() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", APIConstants.API_KEY);
		params.put("language", "zh_CN");
		request(APIConstants.GET_HEROS, params, false);
	}

	public void getOnlineNum() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", APIConstants.API_KEY);
		params.put("appid", "570");
		request(APIConstants.GET_ONLINE_NUM, params, false);
	}

	// public void getMatchDetails(ArrayList<String> matchIDs) {
	// // Map<String, String> params = new HashMap<String, String>();
	// // params.put("key", APIConstants.API_KEY);
	// // params.put("match_id", matchID);
	// requestDetails(APIConstants.GET_MATCH_DETAILS, matchIDs, false);
	// }
	public void getMatchDetails(String matchID) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", APIConstants.API_KEY);
		params.put("match_id", matchID);
		request(APIConstants.GET_MATCH_DETAILS, params, true);
	}

	public void getPlayerDetail(String steamID) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", APIConstants.API_KEY);
		params.put("steamids", steamID);
		request(APIConstants.GET_PLAYER_SUMMARIES, params, true);
	}

	public void getBoard(String division) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		params.put("division", division);
		request(APIConstants.GET_BOARD, params, true);
	}

}
