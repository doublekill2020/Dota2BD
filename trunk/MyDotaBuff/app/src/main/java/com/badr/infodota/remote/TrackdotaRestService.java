package com.badr.infodota.remote;

import com.badr.infodota.api.trackdota.core.CoreResult;
import com.badr.infodota.api.trackdota.game.GamesResult;
import com.badr.infodota.api.trackdota.live.LiveGame;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 16:39
 */
public interface TrackdotaRestService {
    @GET("/game/{gameId}/live.json")
    LiveGame getLiveGame(@Path("gameId") long gameId);
    @GET("/game/{gameId}/core.json")
    CoreResult getGameCoreData(@Path("gameId") long gameId);
    @GET("/games_v2.json")
    GamesResult getGames();
}
