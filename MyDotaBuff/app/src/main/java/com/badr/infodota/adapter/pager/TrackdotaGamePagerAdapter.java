package com.badr.infodota.adapter.pager;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.util.Pair;

import com.badr.infodota.api.trackdota.core.CoreResult;
import com.badr.infodota.api.trackdota.live.LiveGame;
import com.badr.infodota.fragment.trackdota.game.CommonInfo;
import com.badr.infodota.fragment.trackdota.game.Graphs;
import com.badr.infodota.fragment.trackdota.game.LogList;
import com.badr.infodota.fragment.trackdota.game.MapAndHeroes;
import com.badr.infodota.fragment.trackdota.game.Statistics;
import com.badr.infodota.util.Refresher;
import com.badr.infodota.util.Updatable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.edu.mydotabuff.R;

/**
 * Created by ABadretdinov
 * 14.04.2015
 * 16:49
 */
public class TrackdotaGamePagerAdapter extends FragmentPagerAdapter {
    private CoreResult coreResult;
    private LiveGame liveGame;
    private String[] titles;
    private Refresher refresher;
    private Map<Integer,Updatable<Pair<CoreResult,LiveGame>>> fragmentsMap =new HashMap<Integer,Updatable<Pair<CoreResult,LiveGame>>>();
    public TrackdotaGamePagerAdapter(Context context,FragmentManager fragmentManager,Refresher refresher, CoreResult coreResult,LiveGame liveGame){
        super(fragmentManager);
        titles=context.getResources().getStringArray(R.array.trackdota_game);
        this.coreResult=coreResult;
        this.liveGame=liveGame;
        this.refresher=refresher;
    }
    @Override
    public Fragment getItem(int position){
        if(fragmentsMap.containsKey(position)){
            return (Fragment) fragmentsMap.get(position);
        }
        switch (position){
            default:
            case 0:
                CommonInfo commonInfo=CommonInfo.newInstance(refresher,coreResult,liveGame);
                fragmentsMap.put(position,commonInfo);
                return commonInfo;
            case 1:
                MapAndHeroes mapAndHeroes=MapAndHeroes.newInstance(refresher,coreResult,liveGame);
                fragmentsMap.put(position,mapAndHeroes);
                return mapAndHeroes;
            case 2:
                Graphs graphs=Graphs.newInstance(refresher,coreResult,liveGame);
                fragmentsMap.put(position,graphs);
                return graphs;
            case 3:
                Statistics statistics=Statistics.newInstance(refresher,coreResult,liveGame);
                fragmentsMap.put(position,statistics);
                return statistics;
            case 4:
                LogList logList= LogList.newInstance(refresher,coreResult,liveGame);
                fragmentsMap.put(position,logList);
                return logList;

        }
    }

    @Override
    public int getCount(){
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return titles[position];
    }

    public void update(CoreResult coreResult,LiveGame liveGame){
        this.coreResult=coreResult;
        this.liveGame=liveGame;
        Set<Integer> keySet=fragmentsMap.keySet();
        for(Integer key:keySet){
            Updatable<Pair<CoreResult,LiveGame>> updatable=fragmentsMap.get(key);
            updatable.onUpdate(Pair.create(coreResult, liveGame));
        }
    }
}
