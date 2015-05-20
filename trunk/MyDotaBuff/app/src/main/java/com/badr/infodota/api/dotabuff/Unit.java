package com.badr.infodota.api.dotabuff;

import com.badr.infodota.util.HasId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * User: Histler
 * Date: 21.01.14
 */
public class Unit implements Serializable, HasId {
    /*
    * Player
    * Team
    * Match
    * */
    private String type;
    /*
    * real player/team name
    * */
    private String name;

    private String localName;


    private Groups group = Groups.NONE;
    /*
    * src to steam icon (if exists)
    * */
    private String icon;
    /*
    * url for player. actually it's  /players/109179476
    * so we need to delete /players/ and get only account_id=109179476
    * */
    private String url;
    /*
    * этого в апи нет, это мы сами вытаскиваем.
    * */
    private long accountId;

    private boolean searched = false;


    public Unit() {
    }

    public boolean isSearched() {
        return searched;
    }

    public void setSearched(boolean searched) {
        this.searched = searched;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        String[] parsed = url.split("/");
        if (parsed.length > 0) {
            accountId = Long.valueOf(parsed[parsed.length - 1]);
        }
    }

    public long getAccountId() {
        if (accountId == 0 && url != null) {
            String[] parsed = url.split("/");
            if (parsed.length > 0) {
                accountId = Long.valueOf(parsed[parsed.length - 1]);
            }
        }
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }

    @Override
    public long getId() {
        return accountId;
    }

    @Override
    public void setId(long id) {
        this.accountId = id;
    }

    public enum Groups {
        NONE,
        FRIEND,
        PRO
    }
    public static class List extends ArrayList<Unit> {
        public List() {
        }

        public List(Collection<? extends Unit> collection) {
            super(collection);
        }
    }
}
