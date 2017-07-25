package cn.edu.mydotabuff.util;

import java.util.HashMap;
import java.util.Map;

import cn.edu.mydotabuff.base.BaseRequestInterface;
import cn.edu.mydotabuff.common.http.APIConstants;
import cn.edu.mydotabuff.common.http.IInfoReceive;

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

	public void getMatchDetails(String matchID) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", APIConstants.API_KEY);
		params.put("match_id", matchID);
		request(APIConstants.GET_MATCH_DETAILS, params, false);
	}

	public void getPlayerDetail(String steamID, boolean isShowDlg) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", APIConstants.API_KEY);
		params.put("steamids", steamID);
		request(APIConstants.GET_PLAYER_SUMMARIES, params, isShowDlg);
	}

	public void getBoard(String division) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		params.put("division", division);
		request(APIConstants.GET_BOARD, params, true);
	}

	public void getFriendList(String steamid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", APIConstants.API_KEY);
		params.put("appid", "570");
		params.put("steamid", steamid);
		params.put("relationship", "friend");
		request(APIConstants.GET_FRIEND_LIST, params, true);
	}

	/**
	 * 
	 * @param index
	 *            0-全部 1-刀塔新闻 2-赛事资讯 3-版本公告
	 * @param page
	 *            0-第一页
	 */
	public void getDota2News(int index, int page) {
		StringBuilder sb = new StringBuilder();
		switch (index) {
		case 0:
			sb.append(APIConstants.INDEX1_URL);
			break;
		case 1:
			sb.append(APIConstants.INDEX2_URL);
			break;
		case 2:
			sb.append(APIConstants.INDEX3_URL);
			break;
		case 3:
			sb.append(APIConstants.INDEX4_URL);
			break;
		default:
			break;
		}
		if (page == 0) {
			sb.append(".html");
		} else {
			sb.append(page + ".html");
		}
		request(sb.toString(), new HashMap<String, String>(), true);
	}
}
