package com.badr.infodota.api.playersummaries.friends;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 21.04.14
 * Time: 16:46
 */
public class FriendsList {
    private List<Friend> friends;

    public FriendsList() {
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }
}
