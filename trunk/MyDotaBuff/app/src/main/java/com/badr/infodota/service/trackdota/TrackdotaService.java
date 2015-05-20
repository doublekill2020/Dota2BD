package com.badr.infodota.service.trackdota;

import android.content.Context;

import com.badr.infodota.InitializingBean;
import com.badr.infodota.api.trackdota.core.CoreResult;
import com.badr.infodota.api.trackdota.game.GamesResult;
import com.badr.infodota.api.trackdota.live.LiveGame;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 17:17
 */
public interface TrackdotaService extends InitializingBean {

    LiveGame getLiveGame(Context context, long gameId);

    CoreResult getGameCoreData(Context context, long gameId);

    GamesResult getGames();
}
