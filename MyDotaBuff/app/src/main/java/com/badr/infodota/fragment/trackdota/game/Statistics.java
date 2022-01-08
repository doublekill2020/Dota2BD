package com.badr.infodota.fragment.trackdota.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.badr.infodota.api.heroes.Hero;
import com.badr.infodota.api.trackdota.GameManager;
import com.badr.infodota.api.trackdota.TrackdotaUtils;
import com.badr.infodota.api.trackdota.core.CoreResult;
import com.badr.infodota.api.trackdota.core.Player;
import com.badr.infodota.api.trackdota.live.LiveGame;
import com.badr.infodota.api.trackdota.live.LivePlayer;
import com.badr.infodota.api.trackdota.live.LiveTeam;
import com.badr.infodota.util.Refresher;
import com.badr.infodota.util.TrackUtils;
import com.badr.infodota.util.Updatable;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.edu.mydotabuff.R;

/**
 * Created by ABadretdinov
 * 18.04.2015
 * 11:33
 */
public class Statistics extends Fragment implements Updatable<Pair<CoreResult,LiveGame>> {
    private Refresher refresher;
    private CoreResult coreResult;
    private LiveGame liveGame;
    private SwipeRefreshLayout mScrollContainer;
    private LinearLayout mStatHolder;
    private Spinner mStatTypeSpinner;
    int mSelectedStat;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public static Statistics newInstance(Refresher refresher,CoreResult coreResult,LiveGame liveGame){
        Statistics fragment=new Statistics();
        fragment.refresher=refresher;
        fragment.coreResult=coreResult;
        fragment.liveGame=liveGame;
        return fragment;
    }

    final private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if(refresher!=null) {
                mScrollContainer.setRefreshing(true);
                refresher.onRefresh();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.trackdota_game_statistics,container,false);

        mScrollContainer = (SwipeRefreshLayout) root.findViewById(R.id.listContainer);
        mScrollContainer.setOnRefreshListener(mOnRefreshListener);
        mScrollContainer.setColorSchemeResources(R.color.primary);

        mStatHolder= (LinearLayout) root.findViewById(R.id.stat_holder);
        mStatTypeSpinner= (Spinner) root.findViewById(R.id.stat_type);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_pic)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        imageLoader = ImageLoader.getInstance();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.trackdota_player_stats));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStatTypeSpinner.setAdapter(adapter);
        mStatTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mSelectedStat != position) {
                    mSelectedStat = position;
                    updateStatistic();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        updateStatistic();
    }

    @Override
    public void onUpdate(Pair<CoreResult, LiveGame> entity) {
        mScrollContainer.setRefreshing(false);
        this.coreResult=entity.first;
        this.liveGame=entity.second;
        updateStatistic();
    }

    private void updateStatistic() {
        mStatHolder.removeAllViews();
        if(liveGame!=null&&liveGame.getStatus()>=2){
            GameManager gameManager=GameManager.getInstance();
            List<StatEntry> statEntries=new ArrayList<>();
            LiveTeam radiant=liveGame.getRadiant();
            for(LivePlayer livePlayer:radiant.getPlayers()){
                StatEntry entry=new StatEntry();
                Player player=gameManager.getPlayer(livePlayer.getAccountId());
                entry.setPlayerName(player.getName());
                Hero hero=gameManager.getHero(player.getHeroId());
                entry.setHeroDotaId(hero != null ? hero.getDotaId() : null);
                entry.setTeam(TrackdotaUtils.RADIANT);
                entry.setDisplayValue(getDisplayValue(livePlayer));
                entry.setSortValue(getSortValue(livePlayer));
                entry.setLivePlayer(livePlayer);
                statEntries.add(entry);
            }
            LiveTeam dire=liveGame.getDire();
            for(LivePlayer livePlayer:dire.getPlayers()){
                StatEntry entry=new StatEntry();
                Player player=gameManager.getPlayer(livePlayer.getAccountId());
                entry.setPlayerName(player.getName());
                Hero hero=gameManager.getHero(player.getHeroId());
                entry.setHeroDotaId(hero != null ? hero.getDotaId() : null);
                entry.setTeam(TrackdotaUtils.DIRE);
                entry.setDisplayValue(getDisplayValue(livePlayer));
                entry.setSortValue(getSortValue(livePlayer));
                entry.setLivePlayer(livePlayer);
                statEntries.add(entry);
            }
            Collections.sort(statEntries);
            LayoutInflater inflater= (LayoutInflater) mStatHolder.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int radiantColor=getResources().getColor(R.color.radiant_dark);
            int direColor=getResources().getColor(R.color.dire_dark);
            for(StatEntry entry:statEntries){
                View row=inflater.inflate(R.layout.trackdota_game_stat_row,mStatHolder,false);
                imageLoader.displayImage(TrackUtils.getHeroMiniImage(entry.getHeroDotaId()), (ImageView) row.findViewById(R.id.hero_icon),options);
                ((TextView)row.findViewById(R.id.player_name)).setText(entry.getPlayerName());
                if(entry.getTeam()==TrackdotaUtils.RADIANT){
                    row.setBackgroundColor(radiantColor);
                }
                else {
                    row.setBackgroundColor(direColor);
                }
                ((TextView)row.findViewById(R.id.stat_value)).setText(entry.getDisplayValue());
                row.setOnClickListener(new TrackdotaUtils.OnLivePlayerClickListener(entry.getLivePlayer(),entry.getPlayerName()));
                mStatHolder.addView(row);
            }
        }

    }
    private String getDisplayValue(LivePlayer livePlayer){
        switch (mSelectedStat){
            default:
                return getString(R.string.stat_not_found);
            case 0:
                return String.valueOf(livePlayer.getNetWorth());
            case 1:
                return String.valueOf(livePlayer.getLevel());
            case 2:
                return String.valueOf(livePlayer.getGold());
            case 3:
                return livePlayer.getKills()+" / "+livePlayer.getDeath()+" / "+livePlayer.getAssists();
            case 4:
                return livePlayer.getGpm()+" / "+livePlayer.getXpm();
            case 5:
                return livePlayer.getLastHits()+" / "+livePlayer.getDenies();
            case 6:
                return String.valueOf(livePlayer.getKills());
            case 7:
                return String.valueOf(livePlayer.getDeath());
            case 8:
                return String.valueOf(livePlayer.getAssists());
            case 9:
                return String.valueOf(livePlayer.getGpm());
            case 10:
                return String.valueOf(livePlayer.getXpm());
            case 11:
                return String.valueOf(livePlayer.getLastHits());
            case 12:
                return String.valueOf(livePlayer.getDenies());
        }
    }
    private int getSortValue(LivePlayer livePlayer){
        switch (mSelectedStat){
            default:
                return 0;
            case 0:
                return (int)livePlayer.getNetWorth();
            case 1:
                return livePlayer.getLevel();
            case 2:
                return (int)livePlayer.getGold();
            case 3:
                return (livePlayer.getKills()+livePlayer.getAssists())/(livePlayer.getDeath()!=0?livePlayer.getDeath():1);
            case 4:
                return livePlayer.getGpm();
            case 5:
                return livePlayer.getLastHits()+livePlayer.getDenies();
            case 6:
                return livePlayer.getKills();
            case 7:
                return livePlayer.getDeath();
            case 8:
                return livePlayer.getAssists();
            case 9:
                return livePlayer.getGpm();
            case 10:
                return livePlayer.getXpm();
            case 11:
                return livePlayer.getLastHits();
            case 12:
                return livePlayer.getDenies();

        }
    }

    public static class StatEntry implements Serializable,Comparable{
        private String heroDotaId;
        private String playerName;
        private int team;
        private String displayValue;
        private int sortValue;
        private LivePlayer livePlayer;

        public LivePlayer getLivePlayer() {
            return livePlayer;
        }

        public void setLivePlayer(LivePlayer livePlayer) {
            this.livePlayer = livePlayer;
        }

        public String getHeroDotaId() {
            return heroDotaId;
        }

        public void setHeroDotaId(String heroDotaId) {
            this.heroDotaId = heroDotaId;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public int getTeam() {
            return team;
        }

        public void setTeam(int team) {
            this.team = team;
        }

        public String getDisplayValue() {
            return displayValue;
        }

        public void setDisplayValue(String displayValue) {
            this.displayValue = displayValue;
        }

        public int getSortValue() {
            return sortValue;
        }

        public void setSortValue(int sortValue) {
            this.sortValue = sortValue;
        }
        /*Sort desc*/
        @Override
        public int compareTo(Object another) {
            if(another instanceof StatEntry){
                int value= ((StatEntry) another).getSortValue()-sortValue;
                if(value==0){
                    return displayValue.compareTo(((StatEntry) another).getDisplayValue());
                }
                return value;
            }
            return -1;
        }

        @Override
        public String toString() {
            return playerName;
        }
    }
}
