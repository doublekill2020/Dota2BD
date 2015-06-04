package com.badr.infodota.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.api.heroes.Hero;
import com.badr.infodota.api.items.Item;
import com.badr.infodota.api.trackdota.GameManager;
import com.badr.infodota.api.trackdota.TrackdotaUtils;
import com.badr.infodota.api.trackdota.core.Player;
import com.badr.infodota.api.trackdota.game.Team;
import com.badr.infodota.api.trackdota.live.LogEvent;
import com.badr.infodota.service.item.ItemService;
import com.badr.infodota.util.TrackUtils;
import com.badr.infodota.view.PinnedSectionListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.util.Utils;

/**
 * Created by ABadretdinov
 * 17.04.2015
 * 15:05
 */
public class LogsAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    DisplayImageOptions options;
    private ImageLoader imageLoader;
    private ItemService itemService = BeanContainer.getInstance().getItemService();
    private GameManager gameManager = GameManager.getInstance();
    private List<Object> logs=new ArrayList<Object>();
    private Team radiant;
    private Team dire;


    public LogsAdapter(List<LogEvent> logEvents, Team radiant, Team dire){
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_pic)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        imageLoader = ImageLoader.getInstance();
        this.radiant=radiant;
        this.dire=dire;
        /*add normal sorting*/
        if(logEvents!=null){
            List<LogEvent> events=new ArrayList<>(logEvents);
            Collections.reverse(events);
            for(LogEvent logEvent:events){
                if(!logs.contains(logEvent.getTimestamp())){
                    logs.add(logEvent.getTimestamp());
                }
                logs.add(logEvent);
            }
        }
    }

    @Override
    public int getCount() {
        return logs.size();
    }

    @Override
    public Object getItem(int position) {
        return logs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView=convertView;
        Object object=getItem(position);
        Context context=parent.getContext();
        if(object instanceof LogEvent){
            LogEventHolder holder;
            if(itemView==null||itemView.getTag()==null){
                itemView=((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.trackdota_log_row,parent,false);
                holder=new LogEventHolder();
                holder.heroIcon= (ImageView) itemView.findViewById(R.id.hero_icon);
                holder.text= (TextView) itemView.findViewById(android.R.id.text1);
                holder.itemIcon= (ImageView) itemView.findViewById(R.id.item_icon);
                itemView.setTag(holder);
            }
            else {
                holder= (LogEventHolder) itemView.getTag();
            }
            LogEvent logEvent= (LogEvent) object;
            holder.text.setTextSize(2, 16f);
            holder.heroIcon.setVisibility(View.VISIBLE);
            holder.itemIcon.setVisibility(View.VISIBLE);
            holder.text.setVisibility(View.VISIBLE);
            Resources resources=context.getResources();

            if("kill".equals(logEvent.getAction())){
                holder.itemIcon.setVisibility(View.GONE);
                holder.text.setTextColor(Color.WHITE);
                Player player=gameManager.getPlayer(logEvent.getAccountId());
                imageLoader.displayImage(
                        Utils.getHeroImageMINIUri(Common.getHeroName(new Long(player.getHeroId()).intValue())),
                        holder.heroIcon);
                String playerName=getHtmlColorWrap(player.getName(),player.getTeam()== TrackdotaUtils.RADIANT?resources.getColor(R.color.ally_team):resources.getColor(R.color.enemy_team));
                if(logEvent.getDelta()>1){
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.player_kills_multiple_),
                                            playerName,
                                            logEvent.getDelta()
                                    )
                            )
                    );
                }
                else {
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.player_kills_),
                                            playerName
                                    )
                            )
                    );
                }
            }
            else if("death".equals(logEvent.getAction())){
                holder.itemIcon.setVisibility(View.GONE);
                holder.text.setTextColor(Color.WHITE);
                Player player=gameManager.getPlayer(logEvent.getAccountId());
                imageLoader.displayImage(
                        Utils.getHeroImageMINIUri(Common.getHeroName(new Long(player.getHeroId()).intValue())),
                        holder.heroIcon);
                String playerName=getHtmlColorWrap(player.getName(),player.getTeam()== TrackdotaUtils.RADIANT?resources.getColor(R.color.ally_team):resources.getColor(R.color.enemy_team));
                if(logEvent.getDelta()>1){
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.player_dies_multiple_),
                                            playerName,
                                            logEvent.getDelta()
                                    )
                            )
                    );
                }
                else {
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.player_dies_),
                                            playerName
                                    )
                            )
                    );
                }
            }
            else if("item".equals(logEvent.getAction())){
                Player player=gameManager.getPlayer(logEvent.getAccountId());
                holder.text.setTextColor(Color.WHITE);
                long itemId=Long.valueOf(logEvent.getId());
                imageLoader.displayImage(
                        Utils.getHeroImageMINIUri(Common.getHeroName(new Long(player.getHeroId()).intValue())),
                        holder.heroIcon);
                imageLoader.displayImage(
                        Utils.getItemsImageUri(Common.getItemName(new Long(itemId).intValue())),
                        holder.itemIcon);
                String itemName=getHtmlColorWrap(Common.getItemName(new Long(itemId).intValue()),resources.getColor(R.color.item));
                String playerName=getHtmlColorWrap(player.getName(),player.getTeam()== TrackdotaUtils.RADIANT?resources.getColor(R.color.ally_team):resources.getColor(R.color.enemy_team));
                if((itemId==117||itemId==33)&&logEvent.getDelta()>=1){
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.log_item_pickup),
                                            playerName,
                                            itemName
                                            )
                            )
                    );
                }
                else if(logEvent.getDelta()>=1){
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.log_item_purchased),
                                            playerName,
                                            itemName
                                    )
                            )
                    );
                }
                else if(logEvent.getDelta()==0){
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.log_item_sold),
                                            playerName,
                                            itemName
                                    )
                            )
                    );
                }
                else {
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.log_item_used),
                                            playerName,
                                            itemName
                                    )
                            )
                    );
                }
            }
            else if("tower".equals(logEvent.getAction())){
                holder.itemIcon.setVisibility(View.GONE);
                holder.heroIcon.setVisibility(View.GONE);
                holder.text.setTextColor(resources.getColor(R.color.tower));
                String teamName;
                long delta=logEvent.getDelta();
                /*первые 11 товеров - radiant*/
                if(delta<11){
                    teamName=getHtmlColorWrap(TrackdotaUtils.getTeamName(radiant, TrackdotaUtils.RADIANT), resources.getColor(R.color.ally_team));
                }
                else {
                    teamName=getHtmlColorWrap(TrackdotaUtils.getTeamName(dire, TrackdotaUtils.DIRE), resources.getColor(R.color.enemy_team));
                    delta-=11;
                }
                String line;
                int level;
                String[] lines=context.getResources().getStringArray(R.array.map_tower_line);
                if(delta==10){
                    level=4;
                    line=lines[2];
                }
                else if(delta==9){
                    level=4;
                    line=lines[0];
                }
                else {
                    line=lines[((int) (delta / 3))];
                    level= (int) (1+delta%3);
                }
                holder.text.setText(
                        Html.fromHtml(
                                MessageFormat.format(
                                        context.getString(R.string.log_team_loses_tower),
                                        teamName,
                                        line,
                                        level
                                )
                        )
                );
            }
            else if("barracks".equals(logEvent.getAction())){
                holder.itemIcon.setVisibility(View.GONE);
                holder.heroIcon.setVisibility(View.GONE);
                holder.text.setTextColor(resources.getColor(R.color.barracks));
                long delta=logEvent.getDelta();
                String teamName;
                /*первые 6 бараков - radiant*/
                if(delta<6){
                    teamName= getHtmlColorWrap(TrackdotaUtils.getTeamName(radiant, TrackdotaUtils.RADIANT),resources.getColor(R.color.ally_team));
                }
                else {
                    teamName=getHtmlColorWrap(TrackdotaUtils.getTeamName(dire, TrackdotaUtils.DIRE), resources.getColor(R.color.enemy_team));
                    delta-=6;
                }
                String place=context.getResources().getStringArray(R.array.map_barracks_line)[((int) (delta / 2))];
                String type=delta%2==0?context.getString(R.string.barrack_melee):context.getString(R.string.barrack_ranged);
                holder.text.setText(
                        Html.fromHtml(
                                MessageFormat.format(
                                        context.getString(R.string.log_team_loses_barracks),
                                        teamName,
                                        place,
                                        type
                                )
                        )
                );

            }
            else if("buyback".equals(logEvent.getAction())){
                holder.itemIcon.setVisibility(View.GONE);
                holder.text.setTextColor(resources.getColor(R.color.buyback));
                Player player=gameManager.getPlayer(logEvent.getAccountId());
                imageLoader.displayImage(
                        Utils.getHeroImageMINIUri(Common.getHeroName(new Long(player.getHeroId()).intValue())),
                        holder.heroIcon);
                String playerName=getHtmlColorWrap(player.getName(),player.getTeam()== TrackdotaUtils.RADIANT?resources.getColor(R.color.ally_team):resources.getColor(R.color.enemy_team));
                holder.text.setText(
                        Html.fromHtml(
                                MessageFormat.format(
                                        context.getString(R.string.log_hero_buys_back),
                                        playerName
                                )
                        )
                );
            }
            else if("roshan".equals(logEvent.getAction())){
                holder.itemIcon.setVisibility(View.GONE);
                holder.heroIcon.setVisibility(View.GONE);
                holder.text.setTextSize(2, 20f);
                holder.text.setTextColor(resources.getColor(R.color.roshan));
                if(logEvent.getDelta()<0){
                    holder.text.setText(context.getString(R.string.roshan_death));
                }
                else {
                    holder.text.setText(context.getString(R.string.roshan_respawn));
                }
            }
            else if("win".equals(logEvent.getAction())){
                holder.heroIcon.setVisibility(View.GONE);
                holder.itemIcon.setVisibility(View.GONE);
                holder.text.setTextSize(2, 24f);
                holder.text.setTextColor(Color.WHITE);
                String teamName=logEvent.getDelta()== TrackdotaUtils.RADIANT?
                        getHtmlColorWrap(
                                TrackdotaUtils.getTeamName(
                                        radiant,
                                        TrackdotaUtils.RADIANT
                                ),
                                resources.getColor(R.color.ally_team)
                        )
                        :getHtmlColorWrap(
                        TrackdotaUtils.getTeamName(
                                dire,
                                TrackdotaUtils.DIRE
                        ),
                        resources.getColor(R.color.enemy_team)
                );
                holder.text.setText(
                        Html.fromHtml(
                                MessageFormat.format(
                                        context.getString(R.string.team_wins_),
                                        teamName
                                )
                        )
                );
            }
            else if("rapier".equals(logEvent.getAction())){
                holder.text.setTextColor(resources.getColor(R.color.rapier));
                holder.text.setTextColor(Color.WHITE);
                Player player=gameManager.getPlayer(logEvent.getAccountId());
                long itemId=133;
                imageLoader.displayImage(
                        Utils.getHeroImageMINIUri(Common.getHeroName(new Long(player.getHeroId()).intValue())),
                        holder.heroIcon);

                imageLoader.displayImage(
                        Utils.getItemsImageUri(Common.getItemName(new Long(itemId).intValue())),
                        holder.itemIcon);
                String itemName=getHtmlColorWrap(Common.getItemName(new Long(itemId).intValue()),resources.getColor(R.color.item));
                String playerName=getHtmlColorWrap(player.getName(),player.getTeam()== TrackdotaUtils.RADIANT?resources.getColor(R.color.ally_team):resources.getColor(R.color.enemy_team));
                if(logEvent.getDelta()>0){
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.log_item_pickup),
                                            playerName,
                                            itemName
                                    )
                            )
                    );
                }
                else {
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.log_hero_drops_item),
                                            playerName,
                                            itemName
                                    )
                            )
                    );
                }

            }
        }
        else {
            long time= (long) object;
            itemView=((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.trackdota_log_pinned_header,parent,false);
            long minutes=time/60;
            long seconds=time-minutes*60;
            ((TextView)itemView.findViewById(android.R.id.text1)).setText(minutes + ":" + (seconds < 10 ? "0" : "") + seconds);
        }
        return itemView;
    }
    private String getHtmlColorWrap(String text, int color){
        return "<font color='" + String.format("#%06X", 0xFFFFFF & color) + "'>" + text + "</font>";
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType==1;
    }
    @Override
    public int getItemViewType(int position) {
        return getItem(position) instanceof LogEvent ? 0 : 1;
    }

    public class LogEventHolder{
        ImageView heroIcon;
        TextView text;
        ImageView itemIcon;
    }
}
