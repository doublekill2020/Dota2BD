package cn.edu.mydotabuff.common.http;

public final class APIConstants {

    // Steam API
    public final static String GET_MATCH_HISTORY = "https://api.steampowered" +
            ".com/IDOTA2Match_570/GetMatchHistory/v001/";
    public final static String GET_MATCH_DETAILS = "https://api.steampowered" +
            ".com/IDOTA2Match_570/GetMatchDetails/v001/";
    public final static String GET_HEROS = "https://api.steampowered" +
            ".com/IEconDOTA2_570/GetHeroes/v0001/";
    public final static String GET_PLAYER_SUMMARIES = " https://api.steampowered" +
            ".com/ISteamUser/GetPlayerSummaries/v0002/";
    public final static String ECONOMY_SCHEMA = "https://api.steampowered" +
            ".com/IEconItems_570/GetSchema/v0001/";
    public final static String GET_LEAGUE_LISTING = "https://api.steampowered" +
            ".com/IDOTA2Match_570/GetLeagueListing/v0001/";
    public final static String GET_LIVE_LEAGUE_GAMES = "https://api.steampowered" +
            ".com/IDOTA2Match_570/GetLiveLeagueGames/v0001/";
    public final static String GET_MATCH_HISTORY_BY_SEQUENCE_NUM = "https://api.steampowered" +
            ".com/IDOTA2Match_570/GetMatchHistoryBySequenceNum/v0001/";
    public final static String GET_TEAM_INFO_BY_TEAMID = "https://api.steampowered" +
            ".com/IDOTA2Match_570/GetTeamInfoByTeamID/v001/";
    public final static String API_KEY = "5150B9DBEEFE515FC93F6419F77275BD";
    public final static String GET_ONLINE_NUM = "http://api.steampowered" +
            ".com/ISteamUserStats/GetNumberOfCurrentPlayers/v1";
    public final static String GET_BOARD = "http://www" +
            ".dota2.com/webapi/ILeaderboard/GetDivisionLeaderboard/v0001";
    public final static String GET_FRIEND_LIST = "http://api.steampowered" +
            ".com/ISteamUser/GetFriendList/v1";
    // dota2 news
    public final static String NEWS_URL = "http://www.dota2.com.cn/wapnews";
    public final static String INDEX1_URL = NEWS_URL + "/hotnewsList";
    public final static String INDEX2_URL = NEWS_URL + "/govnews/index";
    public final static String INDEX3_URL = NEWS_URL + "/matchnews/index";
    public final static String INDEX4_URL = NEWS_URL + "/vernews/index";

    public final static String OPEN_DOTA_API = "https://api.opendota.com/api/";
}
