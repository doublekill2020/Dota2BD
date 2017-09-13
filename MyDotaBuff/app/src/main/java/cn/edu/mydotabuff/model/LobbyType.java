package cn.edu.mydotabuff.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static cn.edu.mydotabuff.model.LobbyType.LOBBY_TYPE_1V1_MID;
import static cn.edu.mydotabuff.model.LobbyType.LOBBY_TYPE_BATTLE_CUP;
import static cn.edu.mydotabuff.model.LobbyType.LOBBY_TYPE_COOP_BOTS;
import static cn.edu.mydotabuff.model.LobbyType.LOBBY_TYPE_NORMAL;
import static cn.edu.mydotabuff.model.LobbyType.LOBBY_TYPE_PRACTICE;
import static cn.edu.mydotabuff.model.LobbyType.LOBBY_TYPE_RANKED;
import static cn.edu.mydotabuff.model.LobbyType.LOBBY_TYPE_RANKED_SOLO_MM;
import static cn.edu.mydotabuff.model.LobbyType.LOBBY_TYPE_RANKED_TEAM_MM;
import static cn.edu.mydotabuff.model.LobbyType.LOBBY_TYPE_TOURNAMENT;
import static cn.edu.mydotabuff.model.LobbyType.LOBBY_TYPE_TUTORIAL;

/**
 * Created by nevermore on 2017/7/6 0006.
 */

@IntDef({LOBBY_TYPE_NORMAL, LOBBY_TYPE_PRACTICE, LOBBY_TYPE_TOURNAMENT, LOBBY_TYPE_TUTORIAL, LOBBY_TYPE_COOP_BOTS, LOBBY_TYPE_RANKED_TEAM_MM, LOBBY_TYPE_RANKED_SOLO_MM
        , LOBBY_TYPE_RANKED, LOBBY_TYPE_1V1_MID, LOBBY_TYPE_BATTLE_CUP})
@Retention(RetentionPolicy.SOURCE)
public @interface LobbyType {
    int LOBBY_TYPE_NORMAL = 0;//普通匹配
    int LOBBY_TYPE_PRACTICE = 1;//练习赛
    int LOBBY_TYPE_TOURNAMENT = 2;//联赛
    int LOBBY_TYPE_TUTORIAL = 3;//教程
    int LOBBY_TYPE_COOP_BOTS = 4;//机器人练习赛
    int LOBBY_TYPE_RANKED_TEAM_MM = 5;//组队天梯
    int LOBBY_TYPE_RANKED_SOLO_MM = 6;//单排天梯
    int LOBBY_TYPE_RANKED = 7;//天梯比赛
    int LOBBY_TYPE_1V1_MID = 8;//solo模式
    int LOBBY_TYPE_BATTLE_CUP = 9;//勇士联赛
}
