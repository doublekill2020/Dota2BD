package com.badr.infodota.api.playersummaries.friends;

/**
 * User: ABadretdinov
 * Date: 21.04.14
 * Time: 16:45
 */
public class Friend {
    private String steamid;
    private String relationship;
    private long friend_since;

    public Friend() {
    }

    public String getSteamid() {
        return steamid;
    }

    public void setSteamid(String steamid) {
        this.steamid = steamid;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public long getFriend_since() {
        return friend_since;
    }

    public void setFriend_since(long friend_since) {
        this.friend_since = friend_since;
    }
}
