package com.badr.infodota.util.retrofit;

import com.octo.android.robospice.request.SpiceRequest;

/**
 * Created by ABadretdinov
 * 18.03.2015
 * 15:36
 */
public abstract class TaskRequest<T> extends SpiceRequest<T> {
    public TaskRequest(Class<T> clazz) {
        super(clazz);
    }
    public abstract T loadData() throws Exception;

    @Override
    public T loadDataFromNetwork() throws Exception {
        return loadData();
    }
}
