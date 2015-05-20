package com.badr.infodota.service.match;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.api.heroes.Hero;
import com.badr.infodota.api.matchdetails.MatchDetails;
import com.badr.infodota.api.matchdetails.Player;
import com.badr.infodota.api.matchhistory.Match;
import com.badr.infodota.api.matchhistory.MatchHistoryResultResponse;
import com.badr.infodota.api.matchhistory.PlayerMatch;
import com.badr.infodota.api.matchhistory.PlayerMatchResult;
import com.badr.infodota.service.hero.HeroService;
import com.badr.infodota.util.FileUtils;
import com.google.gson.Gson;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 16:18
 */
public class MatchServiceImpl implements MatchService {

    @Override
    public MatchDetails getMatchDetails(Context context, String matchId) {
            File externalFilesDir = FileUtils.externalFileDir(context);
            String matchResult = FileUtils.getTextFromFile(externalFilesDir.getAbsolutePath() + File.separator + "matches" + File.separator + matchId + ".json");
            MatchDetails result;
            if (TextUtils.isEmpty(matchResult)) {
                result = BeanContainer.getInstance().getSteamService().getMatchDetails(matchId);
                if (result != null) {
                    FileUtils.saveJsonFile(externalFilesDir.getAbsolutePath() + File.separator + "matches" + File.separator + matchId + ".json",
                            result);
                }
            } else {
                result = new Gson().fromJson(matchResult, MatchDetails.class);
            }
            return result;
    }

    @Override
    public PlayerMatchResult getMatches(Context context,Long accountId, Long fromMatchId,
                                                   Long heroId) {
        try {
            MatchHistoryResultResponse result = BeanContainer.getInstance().getSteamService().getMatchHistory(accountId, fromMatchId, heroId);
            if (result != null&&result.getResult()!=null) {
                HeroService heroService= BeanContainer.getInstance().getHeroService();
                List<Match> matches=result.getResult().getMatches();
                PlayerMatchResult playerMatchResult=new PlayerMatchResult();
                playerMatchResult.setTotalMatches(result.getResult().getTotal_results());
                playerMatchResult.setStatus(result.getResult().getStatus());
                playerMatchResult.setStatusDetails(result.getResult().getStatusDetail());
                PlayerMatch.List list=new PlayerMatch.List();
                if(matches!=null) {
                    for (Match match : matches) {
                        PlayerMatch playerMatch = new PlayerMatch();
                        playerMatch.setMatchId(match.getMatch_id());
                        playerMatch.setLobbyType(match.getLobby_type());
                        long timestamp = match.getStart_time();
                        playerMatch.setGameTime(new Date(timestamp * 1000));
                        List<Player> players = match.getPlayers();
                        boolean found = false;
                        for (int i = 0; i < players.size() && !found; i++) {
                            Player player = players.get(i);
                            if (player.getAccount_id() == accountId) {
                                found = true;
                                Hero hero = heroService.getHeroById(context, player.getHero_id());
                                if (hero != null) {
                                    player.setHero(hero);
                                    playerMatch.setPlayer(player);
                                    list.add(playerMatch);
                                }
                            }
                        }
                    }
                }
                playerMatchResult.setPlayerMatches(list);
                return playerMatchResult;
            }else{
                String message = "Failed to get matches for accountId=" + accountId;
                Log.e(MatchServiceImpl.class.getName(), message);
            }
            return null;
        } catch (Exception e) {
            String message = "Failed to get matches, cause: " + e.getMessage();
            Log.e(MatchServiceImpl.class.getName(), message, e);
            return null;
        }
    }
}
