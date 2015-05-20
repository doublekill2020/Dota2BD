package com.badr.infodota.api;

/**
 * User: ABadretdinov
 * Date: 28.08.13
 * Time: 15:35
 */
public interface Constants {
    public static final boolean INITIALIZATION = false;
    public static final int MILLIS_FOR_EXIT =2000;
    public static final String STEAM_API_URL = "https://api.steampowered.com/IDOTA2Match_570/";

    public static final String GITHUB_LAST_APK_URL="https://github.com/Histler/Infodota/blob/master/infodota.apk?raw=true";
    public static final String GITHUB_UPDATE_URL="https://github.com/Histler/Infodota/blob/master/updates.json?raw=true";
    public interface History {
        public static final String SUBURL = STEAM_API_URL + "GetMatchHistory/V001/?key=";
        public static final String START_AT_MATCH_ID = "&start_at_match_id=";
        public static final String LEAGUE_ID = "&league_id=";
    }

    public interface Team {
        public static final String SUBURL = "http://api.steampowered.com/ISteamRemoteStorage/GetUGCFileDetails/v1/?key={0}&appid=570&ugcid={1}";
    }

    public interface DotaBuff {
        public static final String SEARCH_URL = "http://www.dotabuff.com/search?q=";
    }

    public interface Details {
        public static final String SUBURL = STEAM_API_URL + "GetMatchDetails/V001/?key=";
        public static final String MATCH_ID = "&match_id=";
        //player_name=<name> # Search matches with a player name, exact match only
        public static final String PLAYER_NAME = "&player_name=";
        //hero_id=<id> # Search for matches with a specific hero being played, hero id's are in dota/scripts/npc/npc_heroes.txt in your Dota install directory
        public static final String HERO_ID = "&hero_id=";
        //skill=<skill>  # 0 for any, 1 for normal, 2 for high, 3 for very high skill
        public static final String SKILL = "&skill=";
        //date_min=<date> # date in UTC seconds since Jan 1, 1970 (unix time format)
        public static final String DATE_MIN = "&date_min=";
        //date_max=<date> # date in UTC seconds since Jan 1, 1970 (unix time format)
        public static final String DATE_MAX = "&date_max=";
        //account_id=<id> # Steam account id (this is not SteamID, its only the account number portion)
        public static final String ACCOUNT_ID = "&account_id=";
        //league_id=<id> # matches for a particular league
        public static final String LEAGUE_ID = "&league_id=";
        //start_at_match_id=<id> # Start the search at the indicated match id, descending
        public static final String START_AT_MATCH_ID = "&start_at_match_id=";
        //matches_requested=<n> # Defaults is 25 matches, this can limit to less
        public static final String MATCHES_REQUESTED = "&matches_requested=";

    }

    public interface Heroes {
        public static final String SUBURL = "https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?language=eu_us&key=";
        public static final String STATS_URL = "http://dotaheroes.herokuapp.com/heroes/";
        public static final String SB_IMAGE_URL = "http://media.steampowered.com/apps/dota2/images/heroes/{0}_sb.png";
        public static final String VERT_IMAGE_URL = "http://media.steampowered.com/apps/dota2/images/heroes/{0}_vert.jpg";
        public static final String FULL_IMAGE_URL = "http://media.steampowered.com/apps/dota2/images/heroes/{0}_full.png";
        public static final String MINIMAP_IMAGE_URL = "http://dota2.cyborgmatt.com/minimap_icons/{0}_icon.png";
        public static final String DOTA2_HEROPEDIA_URL = "http://www.dota2.com/hero/{0}/?l={1}";
        public static final String DOTA2_WIKI_RESPONSES_URL = "http://dota2.gamepedia.com/{0}_responses";
    }

    public interface TruePicker {
        public static final String SUBURL = "http://truepicker.com/ru/cap?CaptainV4[action_type]=&CaptainV4[action_data]=&CaptainV4[ban_list]=";//&allies=&enemies=8&info=1&count=108&dota2=0&roles=&level=0
    }

    public interface TwitchTV {
        public static final String SUBURL = "https://api.twitch.tv/kraken/";
        public static final String DOTA_GAMES = SUBURL + "streams?game=Dota%202&hls=true";
        public static final String STREAM_SUBURL = SUBURL + "streams/";
        public static final String ACCESS_TOKEN_URL = "https://api.twitch.tv/api/channels/{0}/access_token";
        public static final String M3U8_URL = "http://usher.twitch.tv/api/channel/hls/{0}.m3u8?token={1}&sig={2}";
        public static final String PREVIEW_URL = "http://static-cdn.jtvnw.net/previews-ttv/live_user_{0}-200x100.jpg";
    }

    public interface Cosmetics {
        public static final String SUBURL = "https://api.steampowered.com/IEconItems_570/GetSchema/v0001/?key={0}&language={1}";
        /*public static final String SUBURL="https://raw.githubusercontent.com/SchemaTracker/SteamEcon/master/cache/schema_570.json";*/
        //public static final String SUBURL="http://api.steampowered.com/IEconItems_570/GetSchemaURL/v1/?key={0}&language={1}";
        public static final String PRICES_URL = "http://api.steampowered.com/ISteamEconomy/GetAssetPrices/v0001?language=en&appid=570&key=";
        public static final String PLAYER_ITEMS_URL = "http://api.steampowered.com/IEconItems_570/GetPlayerItems/v0001/?key={0}&SteamID={1}";
        //help http://wiki.teamfortress.com/wiki/WebAPI/GetSchema
        //steam64id = 76561197960265728+account_Id
    }

    public interface Players {
        public static final String SUBURL = "http://api.steampowered.com/isteamuser/getplayersummaries/v0002/?key={0}&steamids={1}";
        public static final String FRIENDS = "http://api.steampowered.com/ISteamUser/GetFriendList/v0001/?key={0}&steamid={1}";
    }

    public interface News {
        public static final String SUBURL = "http://api.steampowered.com/ISteamNews/GetNewsForApp/v0002/?appid=570&count=50&format=json";
        public static final String ENDDATE = "&enddate={0}";
    }

    public interface TI4 {
        public static final String PRIZEPOOL = "http://api.steampowered.com/IEconDOTA2_570/GetTournamentPrizePool/v1?key={0}&leagueid=600";
        //todo отображать часть html в webview: http://stackoverflow.com/questions/12257929/display-a-part-of-the-webpage-on-the-webview-android
    }

	/*  http://dev.dota2.com/showthread.php?t=58317
    * (GetPlayerSummaries)           https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/
	* (GetLeagueListing)             https://api.steampowered.com/IDOTA2Match_570/GetLeagueListing/v0001/ показывает все имеющиеся лиги
	* (GetLiveLeagueGames)           https://api.steampowered.com/IDOTA2Match_570/GetLiveLeagueGames/v0001/  не показывается, т.к. нет билетов
	* картинки лиг берем из шмоток
	* (GetMatchHistoryBySequenceNum) https://api.steampowered.com/IDOTA2Match_570/GetMatchHistoryBySequenceNum/v0001/
	* (GetTeamInfoByTeamID)          https://api.steampowered.com/IDOTA2Match_570/GetTeamInfoByTeamID/v001/
	* (GetHeroesStats)               http://dotaheroes.herokuapp.com/
*/
    //https://developer.valvesoftware.com/wiki/Steam_Web_API#GetNewsForApp_.28v0001.29 описание
    //новости http://api.steampowered.com/ISteamNews/GetNewsForApp/v0002/?appid=570&count=3&maxlength=300&format=json
    //http://wiki.teamfortress.com/wiki/WebAPI#Dota_2
    // todo тип рарок http://api.steampowered.com/IEconDOTA2_570/GetRarities/v1/?language=en&key=54E61DBFB0A2D4A1B24B4C7EC5C5EFFD
    //https://developer.valvesoftware.com/wiki/Steam_Web_API
    //todo http://wiki.teamfortress.com/wiki/WebAPI#Dota_2
    //https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?key=08D221D8D34304557A600C9CEE197124&language=fr
    //чье-то api для будущих матчей - http://dota2matches.pp.ua/index.php/api/matches/get
    //stickylistHeadersListView  SectionIndexerAdapterWrapper
    //http://www.joindota.com/en/matches
}

