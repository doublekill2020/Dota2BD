package com.badr.infodota.service.update;

import android.content.Context;
import android.util.Pair;

import com.badr.infodota.InitializingBean;

/**
 * Created by Badr on 17.02.2015.
 */
public interface UpdateService extends InitializingBean {
    /*
    first - need to update?
    second - message, if needed
    * */
    Pair<Boolean,String> checkUpdate(Context context);

    void loadNewVersion(Context context);
}
