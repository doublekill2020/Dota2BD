package com.badr.infodota.remote.joindota;

import android.content.Context;

import com.badr.infodota.api.joindota.LiveStream;
import com.badr.infodota.api.joindota.MatchItem;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 22.04.14
 * Time: 14:33
 */
public interface JoinDotaRemoteService{
    MatchItem.List getMatchItems(Context context, int page, String extraParams) throws Exception;

    MatchItem updateMatchItem(MatchItem item) throws Exception;

    void getChannelsNames(List<LiveStream> streams) throws Exception;
}
