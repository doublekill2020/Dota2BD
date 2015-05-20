package com.badr.infodota.api.playersummaries.friends;

/**
 * User: ABadretdinov
 * Date: 21.04.14
 * Time: 16:46
 */
public class FriendsResult {
    private FriendsList friendslist;

    public FriendsResult() {
    }

    public FriendsList getFriendslist() {
        return friendslist;
    }

    public void setFriendslist(FriendsList friendslist) {
        this.friendslist = friendslist;
    }
}
