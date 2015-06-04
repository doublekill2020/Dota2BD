package com.badr.infodota.fragment.trackdota.game;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.badr.infodota.api.heroes.Hero;
import com.badr.infodota.api.items.Item;
import com.badr.infodota.api.trackdota.GameManager;
import com.badr.infodota.api.trackdota.TrackdotaUtils;
import com.badr.infodota.api.trackdota.core.CoreResult;
import com.badr.infodota.api.trackdota.core.Player;
import com.badr.infodota.api.trackdota.game.Team;
import com.badr.infodota.api.trackdota.live.LiveGame;
import com.badr.infodota.api.trackdota.live.LivePlayer;
import com.badr.infodota.api.trackdota.live.LiveTeam;
import com.badr.infodota.util.Refresher;
import com.badr.infodota.util.TrackUtils;
import com.badr.infodota.util.Updatable;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json2.JSONException;

import java.io.IOException;

import cn.edu.mydotabuff.DataManager;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.util.Utils;

import static com.badr.infodota.util.TrackUtils.PHONE;
import static com.badr.infodota.util.TrackUtils.TABLET_LANDSCAPE;
import static com.badr.infodota.util.TrackUtils.TABLET_PORTRAIT;

/**
 * Created by ABadretdinov
 * 15.04.2015
 * 12:37
 */
public class MapAndHeroes extends Fragment implements Updatable<Pair<CoreResult, LiveGame>> {
    private Refresher refresher;
    private CoreResult coreResult;
    private LiveGame liveGame;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    private SwipeRefreshLayout mScrollContainer;
    private LinearLayout mHeroesHolder;
    private int mMapWidth;
    private int mMapHeight;
    private GameManager gameManager = GameManager.getInstance();

    private Point[] towers = new Point[]{
            new Point(13, 39), new Point(13, 55), new Point(9, 71), new Point(40, 59),
            new Point(28, 68), new Point(22, 74), new Point(82, 86), new Point(46, 88),
            new Point(26, 87), new Point(15, 79), new Point(18, 82), new Point(21, 13),
            new Point(50, 13), new Point(71, 14), new Point(56, 48), new Point(65, 37),
            new Point(75, 27), new Point(88, 61), new Point(88, 47), new Point(88, 32),
            new Point(79, 19), new Point(82, 22)
    };

    private Point[] barracks = new Point[]{
            new Point(10, 73), new Point(6, 73), new Point(19, 76),
            new Point(17, 74), new Point(22, 84), new Point(22, 88),
            new Point(72, 15), new Point(72, 11), new Point(77, 24),
            new Point(75, 22), new Point(89, 26), new Point(85, 26)
    };

    final private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (refresher != null) {
                mScrollContainer.setRefreshing(true);
                refresher.onRefresh();
            }
        }
    };

    public static MapAndHeroes newInstance(Refresher refresher, CoreResult coreResult, LiveGame liveGame) {
        MapAndHeroes fragment = new MapAndHeroes();
        fragment.refresher = refresher;
        fragment.coreResult = coreResult;
        fragment.liveGame = liveGame;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trackdota_game_map_n_heroes, container, false);

        mScrollContainer = (SwipeRefreshLayout) view.findViewById(R.id.listContainer);
        mScrollContainer.setOnRefreshListener(mOnRefreshListener);
        mScrollContainer.setColorSchemeResources(R.color.primary);

        mHeroesHolder = (LinearLayout) view.findViewById(R.id.heroes_holder);
        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getBoolean(R.bool.is_tablet) && newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mHeroesHolder.setOrientation(LinearLayout.HORIZONTAL);
        } else {
            mHeroesHolder.setOrientation(LinearLayout.VERTICAL);
        }
        /*проверить*/
        //initView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_pic)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        imageLoader = ImageLoader.getInstance();
        initView();
    }

    @Override
    public void onUpdate(Pair<CoreResult, LiveGame> entity) {
        mScrollContainer.setRefreshing(false);
        this.coreResult = entity.first;
        this.liveGame = entity.second;
        initView();
    }

    private void initView() {
        View root = getView();
        Activity activity = getActivity();
        if (liveGame != null && root != null && activity != null) {
            final View map = root.findViewById(R.id.dota_map);
            map.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                @SuppressWarnings("deprecation")
                public void onGlobalLayout() {
                    mMapHeight = map.getHeight();
                    mMapWidth = map.getWidth();
                    map.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    RelativeLayout mapObjectsHolder = (RelativeLayout) getView().findViewById(R.id.map_objects_holder);
                    mapObjectsHolder.removeAllViews();
                    drawBuildings(mapObjectsHolder, liveGame.getTowerState(), liveGame.getBarracksState());
                    if (liveGame.getRadiant() != null) {
                        drawTeam(mapObjectsHolder, liveGame.getRadiant(), TrackdotaUtils.RADIANT);
                    }
                    if (liveGame.getDire() != null) {
                        drawTeam(mapObjectsHolder, liveGame.getDire(), TrackdotaUtils.DIRE);
                    }
                }
            });
            initTeams();
            initTeamPlayers(liveGame.getRadiant(), (ViewGroup) root.findViewById(R.id.radiant_heroes_holder), TrackdotaUtils.RADIANT);
            initTeamPlayers(liveGame.getDire(), (ViewGroup) root.findViewById(R.id.dire_heroes_holder), TrackdotaUtils.DIRE);
        }
    }

    @SuppressWarnings("deprecation")
    private void drawTeam(RelativeLayout holder, LiveTeam team, int align) {
        Activity activity = getActivity();
        LayoutInflater inflater = activity.getLayoutInflater();
        if (team.getPlayers() != null) {
            int radiantColor = getResources().getColor(R.color.radiant_transparent);
            int radiantDeadColor = getResources().getColor(R.color.radiant_transparent_dead);
            int direColor = getResources().getColor(R.color.dire_transparent);
            int direDeadColor = getResources().getColor(R.color.dire_transparent_dead);
            for (final LivePlayer player : team.getPlayers()) {
                RelativeLayout row = (RelativeLayout) inflater.inflate(R.layout.trackdota_map_minihero, holder, false);
                imageLoader.displayImage(
                        Utils.getHeroImageMINIUri(Common.getHeroName(new Long(player.getHeroId()).intValue())),
                        (ImageView) row.findViewById(R.id.image));
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.startHeroDetailActivity(MapAndHeroes.this.getActivity(),
                                Common.getHeroName(new Long(player.getHeroId()).intValue()));
                    }
                });

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int left = (int) (0.95f * player.getPositionX() / 100 * mMapWidth);
                int top = (int) (0.95f * player.getPositionY() / 100 * mMapHeight);
                lp.setMargins(left, top, 0, 0);
                row.setLayoutParams(lp);
                int circleSize = TrackUtils.dpSize(activity, 40);
                Bitmap bitmap = Bitmap.createBitmap(circleSize, circleSize, Bitmap.Config.ARGB_8888);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                Canvas canvas = new Canvas(bitmap);
                switch (align) {
                    case TrackdotaUtils.RADIANT:
                        if (player.getRespawnTimer() > 0) {
                            paint.setColor(radiantDeadColor);
                        } else {
                            paint.setColor(radiantColor);
                        }
                        break;
                    case TrackdotaUtils.DIRE:
                        if (player.getRespawnTimer() > 0) {
                            paint.setColor(direDeadColor);
                        } else {
                            paint.setColor(direColor);
                        }
                        break;
                }
                canvas.drawCircle(circleSize / 2, circleSize / 2, circleSize / 2, paint);
                BitmapDrawable drawable = new BitmapDrawable(activity.getResources(), bitmap);
                if (player.getRespawnTimer() > 0) {
                    row.removeView(row.findViewById(R.id.alive));
                    row.findViewById(R.id.dead).setBackgroundDrawable(drawable);
                } else {
                    row.removeView(row.findViewById(R.id.dead));
                    row.findViewById(R.id.alive).setBackgroundDrawable(drawable);
                }
                holder.addView(row);
            }
        }
    }

    private void drawBuildings(RelativeLayout holder, int towersState, int barracksState) {
        ImageView buildingsImage = new ImageView(getActivity());
        buildingsImage.setMinimumWidth(mMapWidth);
        buildingsImage.setMinimumHeight(mMapHeight);
        Bitmap bitmap = Bitmap.createBitmap(mMapWidth, mMapHeight, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas(bitmap);
        int towerMaxRadius = TrackUtils.dpSize(getActivity(), 7);
        int towerMinRadius = TrackUtils.dpSize(getActivity(), 5);
        int radiantColor = getResources().getColor(R.color.ally_team);
        int radiantInnerColor = getResources().getColor(R.color.radiant_transparent);
        int direColor = getResources().getColor(R.color.enemy_team);
        int direInnerColor = getResources().getColor(R.color.dire_transparent);

        for (int i = 0; i < towers.length; i++) {
            int alive = 1 << i & towersState;
            int left = (int) (towers[i].x / 100f * mMapWidth);
            int top = (int) (towers[i].y / 100f * mMapHeight);

            if (alive == 0) {
                paint.setColor(Color.WHITE);
            }
            /*если 1ая половина, то это Radiant*/
            else if (i < 11) {
                paint.setColor(radiantColor);
            } else {
                paint.setColor(direColor);
            }
            canvas.drawCircle(left, top, towerMaxRadius, paint);

            if (alive == 0) {
                paint.setColor(Color.BLACK);
            } else if (i < 11) {
                paint.setColor(radiantInnerColor);
            } else {
                paint.setColor(direInnerColor);
            }
            canvas.drawCircle(left, top, towerMinRadius, paint);
        }
        int barrackSize = TrackUtils.dpSize(getActivity(), 8);
        int barrackInnerMargin = TrackUtils.dpSize(getActivity(), 2);
        int barrackInnerSize = TrackUtils.dpSize(getActivity(), 6);
        for (int i = 0; i < barracks.length; i++) {
            int alive = 1 << i & barracksState;
            int left = (int) (barracks[i].x / 100f * mMapWidth);
            int top = (int) (barracks[i].y / 100f * mMapHeight);
            if (alive == 0) {
                paint.setColor(Color.WHITE);
            }
            /*если 1ая половина, то это Radiant*/
            else if (i < 6) {
                paint.setColor(radiantColor);
            } else {
                paint.setColor(direColor);
            }
            canvas.drawRect(
                    left,
                    top,
                    left + barrackSize,
                    top + barrackSize,
                    paint);

            if (alive == 0) {
                paint.setColor(Color.BLACK);
            } else if (i < 6) {
                paint.setColor(radiantInnerColor);
            } else {
                paint.setColor(direInnerColor);
            }
            canvas.drawRect(
                    left + barrackInnerMargin,
                    top + barrackInnerMargin,
                    left + barrackInnerSize,
                    top + barrackInnerSize,
                    paint);
        }
        buildingsImage.setAdjustViewBounds(true);
        buildingsImage.setImageBitmap(bitmap);
        holder.addView(buildingsImage);
    }

    private void initTeams() {
        View root = getView();
        if (root != null) {
            Team radiant = coreResult.getRadiant();
            ((TextView) root.findViewById(R.id.radiant_tag)).setText(TrackdotaUtils.getTeamTag(radiant, TrackdotaUtils.RADIANT));
            ((TextView) root.findViewById(R.id.radiant_name)).setText(TrackdotaUtils.getTeamName(radiant, TrackdotaUtils.RADIANT));
            imageLoader.displayImage(TrackdotaUtils.getTeamImageUrl(radiant), (ImageView) root.findViewById(R.id.radiant_logo), options);

            Team dire = coreResult.getDire();
            ((TextView) root.findViewById(R.id.dire_tag)).setText(TrackdotaUtils.getTeamTag(dire, TrackdotaUtils.DIRE));
            ((TextView) root.findViewById(R.id.dire_name)).setText(TrackdotaUtils.getTeamName(dire, TrackdotaUtils.DIRE));
            imageLoader.displayImage(TrackdotaUtils.getTeamImageUrl(dire), (ImageView) root.findViewById(R.id.dire_logo), options);
        }
    }

    private void initTeamPlayers(LiveTeam liveTeam, ViewGroup view, int align) {
        view.removeAllViews();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        int state = TrackUtils.getDeviceState(getActivity());
        int teamColor = getResources().getColor(align == TrackdotaUtils.RADIANT ? R.color.ally_team : R.color.enemy_team);
        if (liveTeam.getPlayers() != null) {
            for (final LivePlayer livePlayer : liveTeam.getPlayers()) {
                LinearLayout playerRow = (LinearLayout) inflater.inflate(R.layout.match_player_row, view, false);
                LinearLayout unitHolder = (LinearLayout) playerRow.findViewById(R.id.unit_holder);
                LinearLayout additionalUnitHolder = (LinearLayout) playerRow.findViewById(R.id.additional_unit_holder);

                switch (state) {
                    case TABLET_LANDSCAPE:
                        unitHolder.setOrientation(LinearLayout.VERTICAL);
                        additionalUnitHolder.setOrientation(LinearLayout.VERTICAL);
                        ((LinearLayout) playerRow.findViewById(R.id.item_holder)).setOrientation(LinearLayout.HORIZONTAL);
                        playerRow.setOrientation(LinearLayout.HORIZONTAL);
                        break;
                    case TABLET_PORTRAIT:
                        unitHolder.setOrientation(LinearLayout.VERTICAL);
                        additionalUnitHolder.setOrientation(LinearLayout.VERTICAL);
                        ((LinearLayout) playerRow.findViewById(R.id.item_holder)).setOrientation(LinearLayout.VERTICAL);
                        playerRow.setOrientation(LinearLayout.HORIZONTAL);
                        break;
                    case PHONE:
                        unitHolder.setOrientation(LinearLayout.HORIZONTAL);
                        additionalUnitHolder.setOrientation(LinearLayout.HORIZONTAL);
                        ((LinearLayout) playerRow.findViewById(R.id.item_holder)).setOrientation(LinearLayout.VERTICAL);
                        playerRow.setOrientation(LinearLayout.VERTICAL);
                        break;
                }
                TextView heroName = (TextView) playerRow.findViewById(R.id.nick);
                heroName.setVisibility(View.VISIBLE);
                heroName.setText(Common.getHeroName(new Long(livePlayer.getHeroId()).intValue()));
                imageLoader.displayImage(
                        Utils.getHeroImageUri(Common.getHeroName(new Long(livePlayer.getHeroId()).intValue())),
                        (ImageView) playerRow.findViewById(R.id.hero_img));
                TextView playerNick = (TextView) playerRow.findViewById(R.id.hero_name);
                playerNick.setTextColor(teamColor);
                final Player player = gameManager.getPlayer(livePlayer.getAccountId());
                if (player != null) {
                    playerNick.setText(player.getName());
                }
                ((TextView) playerRow.findViewById(R.id.player_lvl)).setText(getString(R.string.level) + ": " + livePlayer.getLevel());
                ((TextView) playerRow.findViewById(R.id.kills)).setText(Html.fromHtml(getString(R.string.kills) + " " + livePlayer.getKills()));
                ((TextView) playerRow.findViewById(R.id.death)).setText(Html.fromHtml(getString(R.string.deaths) + " " + livePlayer.getDeath()));
                ((TextView) playerRow.findViewById(R.id.assists)).setText(Html.fromHtml(getString(R.string.assists) + " " + livePlayer.getAssists()));
                ((TextView) playerRow.findViewById(R.id.gold)).setText(Html.fromHtml(getString(R.string.gold) + " " + livePlayer.getGold()));
                ((TextView) playerRow.findViewById(R.id.last_hits)).setText(Html.fromHtml(getString(R.string.last_hits) + " " + livePlayer.getLastHits()));
                ((TextView) playerRow.findViewById(R.id.denies)).setText(Html.fromHtml(getString(R.string.denies) + " " + livePlayer.getDenies()));
                ((TextView) playerRow.findViewById(R.id.gpm)).setText(Html.fromHtml(getString(R.string.gpm) + " " + livePlayer.getGpm()));
                ((TextView) playerRow.findViewById(R.id.xpm)).setText(Html.fromHtml(getString(R.string.xpm) + " " + livePlayer.getXpm()));

                int[] itemViewIds = new int[]{
                        R.id.item0, R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5,
                        R.id.additional_unit_item0, R.id.additional_unit_item1, R.id.additional_unit_item2, R.id.additional_unit_item3, R.id.additional_unit_item4, R.id.additional_unit_item5
                };
                int size = livePlayer.getItemIds().length;
                for (int i = 0; i < size; i++) {
                    ImageView itemView = (ImageView) playerRow.findViewById(itemViewIds[i]);
                    final long itemId = livePlayer.getItemIds()[i];
                    imageLoader.displayImage(
                            Utils.getItemsImageUri(Common.getItemName(new Long(itemId).intValue())),
                            itemView);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Utils.startItemsDetailActivity(
                                        MapAndHeroes.this.getActivity(),
                                        DataManager.getItemsItem(MapAndHeroes.this.getActivity(),
                                                Common.getItemName(new Long(itemId).intValue())));
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });
                }
                for (int i = size; i < itemViewIds.length; i++) {
                    ImageView itemView = (ImageView) playerRow.findViewById(itemViewIds[i]);
                    itemView.setImageResource(R.drawable.default_pic);
                }
                if (size > itemViewIds.length / 2) {
                    additionalUnitHolder.setVisibility(View.VISIBLE);
                } else {
                    additionalUnitHolder.setVisibility(View.GONE);
                }
                /*if not in pick mode*/
                if (liveGame.getStatus() > 1) {
                    playerRow.setOnClickListener(new TrackdotaUtils.OnLivePlayerClickListener(livePlayer, player.getName()));
                }
                view.addView(playerRow);
            }
        }
    }
}
