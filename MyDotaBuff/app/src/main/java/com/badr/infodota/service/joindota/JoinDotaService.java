package com.badr.infodota.service.joindota;

import android.content.Context;

import com.badr.infodota.InitializingBean;
import com.badr.infodota.api.joindota.LiveStream;
import com.badr.infodota.api.joindota.MatchItem;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 22.04.14
 * Time: 18:12
 */
public interface JoinDotaService extends InitializingBean {
    MatchItem.List getMatchItems(Context context, int page, String extraParams);

    MatchItem updateMatchItem(MatchItem item);

    String fillChannelName(List<LiveStream> streams);
}
