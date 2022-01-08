package com.badr.infodota.fragment.match;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.badr.infodota.api.matchdetails.Player;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 21.01.14
 * Time: 15:32
 */
public class MatchPlayersForSideDetails extends ListFragment {
    private List<Player> players;
    private boolean randomSkills;

    public static MatchPlayersForSideDetails newInstance(boolean isRandomSkills, List<Player> players) {
        MatchPlayersForSideDetails fragment = new MatchPlayersForSideDetails();
        fragment.setPlayers(players);
        fragment.setRandomSkills(isRandomSkills);
        return fragment;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setRandomSkills(boolean randomSkills) {
        this.randomSkills = randomSkills;
    }

    public void setPlayersWithListUpdate(boolean randomSkills, List<Player> players) {
        this.players = players;
        this.randomSkills = randomSkills;
        Context context = getActivity();
        if (context != null) {
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Player player = (Player) getListAdapter().getItem(position);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (players != null) {
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ListAdapter adapter = getListAdapter();
        if (adapter != null) {
        }
    }

}
