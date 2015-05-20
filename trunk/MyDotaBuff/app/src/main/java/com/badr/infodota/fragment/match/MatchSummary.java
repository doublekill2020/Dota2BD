package com.badr.infodota.fragment.match;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.api.heroes.Hero;
import com.badr.infodota.api.matchdetails.PickBan;
import com.badr.infodota.api.matchdetails.Result;
import com.badr.infodota.service.hero.HeroService;
import com.badr.infodota.util.GrayImageLoadingListener;
import com.badr.infodota.util.TrackUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cn.edu.mydotabuff.R;

/**
 * User: Histler
 * Date: 22.01.14
 */
public class MatchSummary extends Fragment {
    Result match;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm  dd.MM.yyyy");

    public static MatchSummary newInstance(Result match) {
        MatchSummary fragment = new MatchSummary();
        fragment.setMatch(match);
        return fragment;
    }

    public void setMatch(Result match) {
        this.match = match;
    }

    public void updateWithMatchInfo(Result match) {
        this.match = match;
        if (match != null) {
            initMatch();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.match_summary, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        sdf.setTimeZone(tz);
        getView().findViewById(R.id.match_summary_table).setVisibility(View.GONE);
        if (match != null) {
            initMatch();
        }
    }

    private void initMatch() {
        View root = getView();
        if (root != null) {
            TableLayout table = (TableLayout) root.findViewById(R.id.match_summary_table);
            table.setVisibility(View.VISIBLE);
            ((TextView) root.findViewById(R.id.match_id)).setText(String.valueOf(match.getMatch_id()));

            long timestamp = match.getStart_time();
            String localTime = sdf.format(new Date(timestamp * 1000));
            ((TextView) root.findViewById(R.id.start_time)).setText(localTime);

            long durationInSeconds = match.getDuration();
            long minutes = durationInSeconds / 60;
            long seconds = durationInSeconds - minutes * 60;
            ((TextView) root.findViewById(R.id.match_length))
                    .setText(minutes + ":" + (seconds < 10 ? "0" : "") + seconds);

            String[] lobbyTypes = getResources().getStringArray(R.array.lobby_types);
            ((TextView) root.findViewById(R.id.lobby_type)).setText(
                    match.getLobby_type() != -1 && match.getLobby_type() < lobbyTypes.length ? lobbyTypes[match
                            .getLobby_type()] : "Invalid");

            String[] gameModes = getResources().getStringArray(R.array.game_modes);
            ((TextView) root.findViewById(R.id.game_mode)).setText(match.getGame_mode() <= gameModes.length ? gameModes[Math.max(0, (int) match.getGame_mode() - 1)] : "Invalid");

            if (TextUtils.isEmpty(match.getRadiant_name()) || TextUtils.isEmpty(match.getDire_name())) {
                root.findViewById(R.id.team_names).setVisibility(View.GONE);
            } else {
                root.findViewById(R.id.team_names).setVisibility(View.VISIBLE);
                ((TextView) root.findViewById(R.id.radiant_name)).setText(match.getRadiant_name());
                ((TextView) root.findViewById(R.id.dire_name)).setText(match.getDire_name());
            }
            if (match.getPicks_bans() != null && match.getPicks_bans().size() > 0) {
                TableLayout cmModeTable = (TableLayout) root.findViewById(R.id.cm_mode_table);
                List<PickBan> pickBans = match.getPicks_bans();
                Activity activity = getActivity();
                if (activity != null) {
                    LayoutInflater inflater = activity.getLayoutInflater();
                    BeanContainer container = BeanContainer.getInstance();
                    HeroService heroService = container.getHeroService();
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    for (PickBan pickBan : pickBans) {
                        ViewGroup row = (ViewGroup) inflater.inflate(R.layout.pick_ban, cmModeTable, false);
                        ImageView currentImage;
                        if (pickBan.getTeam() == 0) {
                            currentImage = (ImageView) row.findViewById(R.id.radiant_hero);
                        } else {
                            currentImage = (ImageView) row.findViewById(R.id.dire_hero);
                        }
                        Hero hero = heroService.getHeroById(activity, pickBan.getHero_id());
                        if (hero != null) {
                            if(pickBan.isIs_pick()){
                                imageLoader.displayImage(TrackUtils.getHeroFullImage(hero.getDotaId()), currentImage);
                            }
                            else {
                                imageLoader.displayImage(TrackUtils.getHeroFullImage(hero.getDotaId()), currentImage,
                                        new GrayImageLoadingListener());
                            }
                            //currentImage.setOnClickListener(new HeroInfoActivity.OnDotaHeroClickListener(hero.getId()));
                        }
                        cmModeTable.addView(row);
                    }
                }
            }
        }
    }
}
