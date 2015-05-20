package com.badr.infodota.fragment.trackdota;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.badr.infodota.activity.TrackdotaGameInfoActivity;
import com.badr.infodota.adapter.LiveGamesAdapter;
import com.badr.infodota.api.trackdota.game.EnhancedGame;
import com.badr.infodota.api.trackdota.game.EnhancedMatch;
import com.badr.infodota.fragment.ListFragment;
import com.badr.infodota.util.Refresher;
import com.badr.infodota.util.Updatable;

import java.util.List;

import cn.edu.mydotabuff.R;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 18:40
 */
public class LiveGamesList extends ListFragment implements Updatable<List<EnhancedMatch>> {
    private Refresher refresher;
    public static LiveGamesList newInstance(Refresher refresher){
        LiveGamesList fragment=new LiveGamesList();
        fragment.refresher=refresher;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLayoutId(R.layout.pinned_section_list);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new LiveGamesAdapter(getActivity(), null));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Object object=getListAdapter().getItem(position);
        if(object instanceof EnhancedGame){
            EnhancedGame game= (EnhancedGame) object;
            Intent intent = new Intent(getActivity(), TrackdotaGameInfoActivity.class);
            intent.putExtra("id", game.getId());
            startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        if(refresher!=null) {
            setRefreshing(true);
            refresher.onRefresh();
        }

    }
    @Override
    public void onUpdate(List<EnhancedMatch> entity) {
        setRefreshing(false);
        setListAdapter(new LiveGamesAdapter(getActivity(),entity));
    }
}
