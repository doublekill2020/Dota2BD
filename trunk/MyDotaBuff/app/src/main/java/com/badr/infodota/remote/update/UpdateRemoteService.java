package com.badr.infodota.remote.update;

import android.content.Context;
import android.util.Pair;

import com.badr.infodota.api.Update;
import com.badr.infodota.remote.BaseRemoteService;

/**
 * Created by Badr on 17.02.2015.
 */
public interface UpdateRemoteService extends BaseRemoteService {
    Pair<Update, String> getUpdate(Context context) throws Exception;
}
