package com.badr.infodota.fragment.trackdota.game;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badr.infodota.adapter.LogsAdapter;
import com.badr.infodota.api.trackdota.core.CoreResult;
import com.badr.infodota.api.trackdota.live.LiveGame;
import com.badr.infodota.fragment.ListFragment;
import com.badr.infodota.util.Refresher;
import com.badr.infodota.util.Updatable;

import cn.edu.mydotabuff.R;

/**
 * Created by ABadretdinov
 * 17.04.2015
 * 14:21
 */
public class LogList extends ListFragment implements Updatable<Pair<CoreResult,LiveGame>>{
    private Refresher refresher;
    private LiveGame liveGame;
    private CoreResult coreResult;

    public static LogList newInstance(Refresher refresher,CoreResult coreResult,LiveGame liveGame){
        LogList fragment=new LogList();
        fragment.refresher=refresher;
        fragment.coreResult=coreResult;
        fragment.liveGame=liveGame;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLayoutId(R.layout.pinned_section_list);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRefreshing(false);
        setLogs();
    }

    @Override
    public void onRefresh() {
        if(refresher!=null){
            setRefreshing(true);
            refresher.onRefresh();
        }
    }

    @Override
    public void onUpdate(Pair<CoreResult, LiveGame> entity) {
        setRefreshing(false);
        coreResult=entity.first;
        liveGame=entity.second;
        setLogs();
    }

    private void setLogs() {
        if(liveGame!=null&&coreResult!=null){
            setListAdapter(new LogsAdapter(liveGame.getLog(),coreResult.getRadiant(),coreResult.getDire()));
        }
        else{
            setListAdapter(new LogsAdapter(null,null,null));
        }
    }
}
