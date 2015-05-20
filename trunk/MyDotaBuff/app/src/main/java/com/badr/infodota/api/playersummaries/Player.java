package com.badr.infodota.api.playersummaries;

/**
 * User: Histler
 * Date: 16.04.14
 */
public class Player {
    private String steamid;
    private Long communityvisibilitystate;
    private Long profilestate;
    private String personaname;
    private Long lastlogoff;
    private Long commentpermission;
    private String profileurl;
    private String avatar;
    private String avatarmedium;
    private String avatarfull;
    private Long personastate;
    private String primaryclanid;
    private Long timecreated;
    private Long personastateflags;

    public Player() {
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarfull() {
        return avatarfull;
    }

    public void setAvatarfull(String avatarfull) {
        this.avatarfull = avatarfull;
    }

    public String getAvatarmedium() {
        return avatarmedium;
    }

    public void setAvatarmedium(String avatarmedium) {
        this.avatarmedium = avatarmedium;
    }

    public Long getCommentpermission() {
        return commentpermission;
    }

    public void setCommentpermission(Long commentpermission) {
        this.commentpermission = commentpermission;
    }

    public Long getCommunityvisibilitystate() {
        return communityvisibilitystate;
    }

    public void setCommunityvisibilitystate(Long communityvisibilitystate) {
        this.communityvisibilitystate = communityvisibilitystate;
    }

    public Long getLastlogoff() {
        return lastlogoff;
    }

    public void setLastlogoff(Long lastlogoff) {
        this.lastlogoff = lastlogoff;
    }

    public String getPersonaname() {
        return personaname;
    }

    public void setPersonaname(String personaname) {
        this.personaname = personaname;
    }

    public Long getPersonastate() {
        return personastate;
    }

    public void setPersonastate(Long personastate) {
        this.personastate = personastate;
    }

    public Long getPersonastateflags() {
        return personastateflags;
    }

    public void setPersonastateflags(Long personastateflags) {
        this.personastateflags = personastateflags;
    }

    public String getPrimaryclanid() {
        return primaryclanid;
    }

    public void setPrimaryclanid(String primaryclanid) {
        this.primaryclanid = primaryclanid;
    }

    public Long getProfilestate() {
        return profilestate;
    }

    public void setProfilestate(Long profilestate) {
        this.profilestate = profilestate;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public String getSteamid() {
        return steamid;
    }

    public void setSteamid(String steamid) {
        this.steamid = steamid;
    }

    public Long getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(Long timecreated) {
        this.timecreated = timecreated;
    }
}
