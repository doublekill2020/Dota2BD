package com.badr.infodota.service.trackdota;

import android.content.Context;
import android.util.Log;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.api.dotabuff.Unit;
import com.badr.infodota.api.heroes.Hero;
import com.badr.infodota.api.items.Item;
import com.badr.infodota.api.trackdota.GameManager;
import com.badr.infodota.api.trackdota.core.BanPick;
import com.badr.infodota.api.trackdota.core.CoreResult;
import com.badr.infodota.api.trackdota.core.Player;
import com.badr.infodota.api.trackdota.game.GamesResult;
import com.badr.infodota.api.trackdota.live.LiveGame;
import com.badr.infodota.api.trackdota.live.LivePlayer;
import com.badr.infodota.remote.TrackdotaRestService;
import com.badr.infodota.service.hero.HeroService;
import com.badr.infodota.service.item.ItemService;
import com.badr.infodota.service.player.PlayerService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 17:39
 */
public class TrackdotaServiceImpl implements TrackdotaService {
    private TrackdotaRestService restService;
    private HeroService heroService;
    private ItemService itemService;
    private PlayerService playerService;

    @Override
    public LiveGame getLiveGame(Context context,long gameId) {
        try {
            LiveGame liveGame= restService.getLiveGame(gameId);
            if(liveGame!=null){
                GameManager gameManager= GameManager.getInstance();
                if(liveGame.getRadiant()!=null&&liveGame.getRadiant().getPlayers()!=null){
                    for(LivePlayer player:liveGame.getRadiant().getPlayers()){
                        initPlayer(context, gameManager, player);
                    }
                }
                if(liveGame.getDire()!=null&&liveGame.getDire().getPlayers()!=null){
                    for(LivePlayer player:liveGame.getDire().getPlayers()){
                        initPlayer(context, gameManager, player);
                    }
                }
            }
            return liveGame;
        } catch (Exception e) {
            String message = "Failed to get trackdota live game, cause:" + e.getMessage();
            Log.e(getClass().getName(), message);
        }
        return null;
    }

    private void initPlayer(Context context,GameManager gameManager, LivePlayer player) {
        if(!gameManager.containsItem(player.getHeroId())){
            Hero hero=heroService.getHeroById(context,player.getHeroId());
            if(hero!=null){
                gameManager.addHero(hero);
            }
        }
        if(player.getItemIds()!=null){
            for(long itemId:player.getItemIds()){
                if(!gameManager.containsItem(itemId)){
                    Item item=itemService.getItemById(context,itemId);
                    if(item!=null){
                        gameManager.addItem(item);
                    }
                }
            }
        }
    }

    @Override
    public CoreResult getGameCoreData(Context context,long gameId) {
        try {
            CoreResult coreResult=restService.getGameCoreData(gameId);
            if(coreResult!=null) {
                GameManager gameManager = GameManager.getInstance();
                if (coreResult.getPlayers() != null) {
                    List<Long> accountIds=new ArrayList<>();
                    for (Player player : coreResult.getPlayers()) {
                        if (!gameManager.containsPlayer(player.getAccountId())) {
                            gameManager.addPlayer(player);
                            Unit unit=playerService.getAccountById(context,player.getAccountId());
                            if(unit==null){
                                accountIds.add(player.getAccountId());
                            }
                        }
                        if (!gameManager.containsHero(player.getHeroId())) {
                            Hero hero = heroService.getHeroById(context, player.getHeroId());
                            if (hero != null) {
                                gameManager.addHero(hero);
                            }
                        }
                    }
                    if(accountIds.size()>0){
                        Unit.List accounts=playerService.loadAccounts(accountIds);
                        for(Unit account:accounts){
                            account.setGroup(Unit.Groups.PRO);
                            playerService.saveAccount(context,account);
                        }
                    }
                }
                addHeroesList(context, gameManager, coreResult.getRadiantPicks());
                addHeroesList(context, gameManager, coreResult.getRadiantBans());
                addHeroesList(context, gameManager, coreResult.getDirePicks());
                addHeroesList(context, gameManager, coreResult.getDireBans());
            }
            return coreResult;
        } catch (Exception e) {
            String message = "Failed to get trackdota core data, cause:" + e.getMessage();
            Log.e(getClass().getName(), message);
        }
        return null;
    }

    private void addHeroesList(Context context, GameManager gameManager, List<BanPick> list) {
        if(list!=null) {
            for (BanPick banPick : list) {
                if (!gameManager.containsHero(banPick.getHeroId())) {
                    Hero hero = heroService.getHeroById(context, banPick.getHeroId());
                    if (hero != null) {
                        gameManager.addHero(hero);
                    }
                }
            }
        }
    }

    @Override
    public GamesResult getGames() {
        try {
            return restService.getGames();
        } catch (Exception e) {
            String message = "Failed to get trackdota games, cause:" + e.getMessage();
            Log.e(getClass().getName(), message);
        }
        return null;
    }

    @Override
    public void initialize() {
        BeanContainer container= BeanContainer.getInstance();
        restService = container.getTrackdotaRestService();
        heroService = container.getHeroService();
        itemService=container.getItemService();
        playerService=container.getPlayerService();
    }
}
