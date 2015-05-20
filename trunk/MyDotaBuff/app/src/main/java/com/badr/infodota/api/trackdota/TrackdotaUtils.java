package com.badr.infodota.api.trackdota;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

//import com.badr.infodota.activity.MatchPlayerInfoActivity;
import com.badr.infodota.api.AbilityUpgrade;
import com.badr.infodota.api.dotabuff.Unit;
import com.badr.infodota.api.matchdetails.AdditionalUnit;
import com.badr.infodota.api.trackdota.game.Team;
import com.badr.infodota.api.trackdota.live.Ability;
import com.badr.infodota.api.trackdota.live.LivePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.mydotabuff.R;

/**
 * Created by ABadretdinov
 * 16.04.2015
 * 12:11
 */
public class TrackdotaUtils {
    public static final int RADIANT=0;
    public static final int DIRE=1;
    public static String getTeamName(Team team,int align){
        return team!=null&& !TextUtils.isEmpty(team.getName())?team.getName():align==RADIANT?"Radiant":"Dire";
    }
    public static String getTeamTag(Team team,int align){
        return team!=null&& !TextUtils.isEmpty(team.getTag())?team.getTag():align==RADIANT?"Radiant":"Dire";
    }
    public static String getTeamImageUrl(Team team){
        return "http://www.trackdota.com/data/images/teams/"+team.getId()+".png";
    }
    public static String getLeagueImageUrl(long leagueId){
        return "http://www.trackdota.com/data/images/leagues/"+leagueId+".jpg";
    }
    public static String getGameStatus(Context context,int status){
        switch (status){
            case 1:
                return context.getString(R.string.in_hero_selection);
            case 2:
                return context.getString(R.string.waiting_for_horn);
            case 3:
                return context.getString(R.string.in_progress);
            case 4:
                return context.getString(R.string.finished);
            default:
                return context.getString(R.string.match_status_unknown);

        }
    }
    public static class OnLivePlayerClickListener implements View.OnClickListener{
        private LivePlayer livePlayer;
        private String playerName;
        public OnLivePlayerClickListener(LivePlayer livePlayer,String playerName) {
            this.livePlayer = livePlayer;
            this.playerName=playerName;
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            com.badr.infodota.api.matchdetails.Player matchPlayer = new com.badr.infodota.api.matchdetails.Player();
            matchPlayer.setHero_id((int) livePlayer.getHeroId());
            matchPlayer.setKills(livePlayer.getKills());
            matchPlayer.setDeaths(livePlayer.getDeath());
            matchPlayer.setAssists(livePlayer.getAssists());
            matchPlayer.setGold(livePlayer.getGold());
            matchPlayer.setLastHits(livePlayer.getLastHits());
            matchPlayer.setDenies(livePlayer.getDenies());
            matchPlayer.setXpPerMin(livePlayer.getXpm());
            matchPlayer.setGoldPerMin(livePlayer.getGpm());
            matchPlayer.setLevel(livePlayer.getLevel());
            List<Ability> abilities = livePlayer.getAbilities();
                                /*this shit with level may not work, that's why we need dummy */
            AbilityUpgrade[] upgrades = new AbilityUpgrade[livePlayer.getLevel()];
            for (int i = 0, size = abilities.size(); i < size; i++) {
                Ability ability = abilities.get(i);
                int[] abBuild = ability.getBuild();
                for (int index = 0; index < abBuild.length; index++) {
                    if (abBuild[index] == 1) {
                        AbilityUpgrade upgrade = new AbilityUpgrade();
                        upgrade.setAbility(ability.getId());
                        upgrade.setLevel(index + 1);
                        if(upgrades.length<=index){
                            AbilityUpgrade[] dummy=new AbilityUpgrade[index+1];
                            System.arraycopy(upgrades,0,dummy,0,upgrades.length);
                            upgrades=dummy;
                        }
                        upgrades[index] = upgrade;
                    }
                }
            }
                            /*delete empty upgrades (if player has extra level points)*/
            int newSize=upgrades.length;
            while (newSize>0&&upgrades[newSize-1]==null){
                newSize--;
            }
            if(newSize!=upgrades.length){
                upgrades= Arrays.copyOf(upgrades, newSize);
            }
            List<AbilityUpgrade> abilityUpgrades = Arrays.asList(upgrades);

            matchPlayer.setAbilityUpgrades(abilityUpgrades);
            AdditionalUnit au = new AdditionalUnit();
            long[] itemIds = livePlayer.getItemIds();
            switch (itemIds.length) {
                default:
                case 12:
                    au.setItem5((int) itemIds[11]);
                case 11:
                    au.setItem4((int) itemIds[10]);
                case 10:
                    au.setItem3((int) itemIds[9]);
                case 9:
                    au.setItem2((int) itemIds[8]);
                case 8:
                    au.setItem1((int) itemIds[7]);
                case 7:
                    au.setItem0((int) itemIds[6]);
                    List<AdditionalUnit> additionalUnits = new ArrayList<AdditionalUnit>();
                    matchPlayer.setAdditionalUnits(additionalUnits);
                case 6:
                    matchPlayer.setItem5((int) itemIds[5]);
                case 5:
                    matchPlayer.setItem4((int) itemIds[4]);
                case 4:
                    matchPlayer.setItem3((int) itemIds[3]);
                case 3:
                    matchPlayer.setItem2((int) itemIds[2]);
                case 2:
                    matchPlayer.setItem1((int) itemIds[1]);
                case 1:
                    matchPlayer.setItem0((int) itemIds[0]);
            }
            matchPlayer.setRespawnTimer(livePlayer.getRespawnTimer());
            matchPlayer.setUltCooldown(livePlayer.getUltCooldown());
            matchPlayer.setUltState(livePlayer.getUltState());
            matchPlayer.setNetWorth(livePlayer.getNetWorth());
            matchPlayer.setLeaverStatus(0);
            Unit account = new Unit();
            account.setAccountId(livePlayer.getAccountId());
            account.setName(playerName);
            matchPlayer.setAccount(account);
            matchPlayer.setAccount_id(livePlayer.getAccountId());

//            Intent intent = new Intent(context, MatchPlayerInfoActivity.class);
//            intent.putExtra("player", matchPlayer);
//            context.startActivity(intent);
        }
    }
}
