package com.badr.infodota.service.match;

import android.content.Context;

import com.badr.infodota.api.matchdetails.MatchDetails;
import com.badr.infodota.api.matchhistory.PlayerMatchResult;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 16:18
 */
public interface MatchService{
    MatchDetails getMatchDetails(Context context, String matchId);

    PlayerMatchResult getMatches(Context context, Long accountId, Long fromMatchId, Long heroId);
}
