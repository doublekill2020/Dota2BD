package com.badr.infodota.api.trackdota;

import com.badr.infodota.api.heroes.Hero;
import com.badr.infodota.api.items.Item;
import com.badr.infodota.api.trackdota.core.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ABadretdinov
 * 16.04.2015
 * 14:45
 */
public class GameManager {
    private Map<Long,Player> players=new HashMap<Long,Player>();
    private Map<Long,Hero> heroes=new HashMap<Long,Hero>();
    private Map<Long,Item> items=new HashMap<Long,Item>();
    private static GameManager instance=null;


    public static GameManager getInstance(){
        if(instance==null){
            instance=new GameManager();
        }
        return instance;
    }
    public Player getPlayer(long accountId){
        return players.get(accountId);
    }
    public Hero getHero(long heroId){
        return heroes.get(heroId);
    }
    public Item getItem(long itemId){
        return items.get(itemId);
    }
    public boolean containsPlayer(long accountId){
        return players.containsKey(accountId);
    }
    public boolean containsHero(long heroId){
        return heroes.containsKey(heroId);
    }
    public boolean containsItem(long itemId){
        return items.containsKey(itemId);
    }

    public void addPlayer(Player player){
        players.put(player.getAccountId(),player);
    }
    public void addHero(Hero hero){
        heroes.put(hero.getId(),hero);
    }
    public void addItem(Item item){
        items.put(item.getId(),item);
    }
    public static void clear(){
        instance=null;
    }
}
