package com.badr.infodota.service.joindota;

import android.content.Context;
import android.util.Log;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.api.joindota.LiveStream;
import com.badr.infodota.api.joindota.MatchItem;
import com.badr.infodota.remote.joindota.JoinDotaRemoteService;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 22.04.14
 * Time: 18:13
 */
public class JoinDotaServiceImpl implements JoinDotaService {
    private JoinDotaRemoteService service;

    @Override
    public MatchItem.List getMatchItems(Context context, int page, String extraParams) {
        try {
            MatchItem.List result = service.getMatchItems(context, page, extraParams);
            if (result == null) {
                Log.e(JoinDotaServiceImpl.class.getName(), "Failed to get joinDota match list");
            }
            return result;
        } catch (Exception e) {
            String message = "Failed to get joinDota match list, cause: " + e.getMessage();
            Log.e(JoinDotaServiceImpl.class.getName(), message, e);
            return null;
        }
    }

    @Override
    public MatchItem updateMatchItem(MatchItem item) {
        try {
            MatchItem result = service.updateMatchItem(item);
            if (result != null) {
                return result;
            }else {
                String errorMsg = "Failed to get joinDota match item";
                Log.e(JoinDotaServiceImpl.class.getName(), errorMsg);
            }
        } catch (Exception e) {
            String message = "Failed to get joinDota match item, cause: " + e.getMessage();
            Log.e(JoinDotaServiceImpl.class.getName(), message, e);
        }
        return null;
    }

    @Override
    public String fillChannelName(List<LiveStream> streams) {
        String message = "";
        try {
            service.getChannelsNames(streams);
        } catch (Exception e) {
            message = "Failed to get live streams, cause: " + e.getMessage();
            Log.e(JoinDotaServiceImpl.class.getName(), message, e);
        }
        return message;
    }

    @Override
    public void initialize() {
        BeanContainer container = BeanContainer.getInstance();
        service = container.getJoinDotaRemoteService();
    }
}
