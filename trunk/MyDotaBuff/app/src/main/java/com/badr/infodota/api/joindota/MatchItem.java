package com.badr.infodota.api.joindota;

import com.badr.infodota.util.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * User: ABadretdinov
 * Date: 22.04.14
 * Time: 14:29
 */
public class MatchItem implements Cloneable, Serializable {
    private Date date;
    private Date detailedDate;
    private Date cestDate;
    private String link;
    private MatchType matchType;
    private String middleText;
    private java.util.List<SubmatchItem> submatches;
    private String team1detailsLink;
    private String team1flagLink;
    private String team1logoLink;
    private String team1name;
    private String team2detailsLink;
    private String team2flagLink;
    private String team2logoLink;
    private String team2name;
    private String title;
    private java.util.List<LiveStream> streams;
    private boolean isSection;

    public MatchItem() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDetailedDate() {
        return detailedDate;
    }

    public void setDetailedDate(Date detailedDate) {
        this.detailedDate = detailedDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public String getMiddleText() {
        return middleText;
    }

    public void setMiddleText(String middleText) {
        this.middleText = middleText;
    }

    public java.util.List<SubmatchItem> getSubmatches() {
        return submatches;
    }

    public void setSubmatches(java.util.List<SubmatchItem> submatches) {
        this.submatches = submatches;
    }

    public String getTeam1detailsLink() {
        return team1detailsLink;
    }

    public void setTeam1detailsLink(String team1detailsLink) {
        this.team1detailsLink = team1detailsLink;
    }

    public String getTeam1flagLink() {
        return team1flagLink;
    }

    public void setTeam1flagLink(String team1flagLink) {
        this.team1flagLink = team1flagLink;
    }

    public String getTeam1logoLink() {
        return team1logoLink;
    }

    public void setTeam1logoLink(String team1logoLink) {
        this.team1logoLink = team1logoLink;
    }

    public String getTeam1name() {
        return team1name;
    }

    public void setTeam1name(String team1name) {
        this.team1name = team1name;
    }

    public String getTeam2detailsLink() {
        return team2detailsLink;
    }

    public void setTeam2detailsLink(String team2detailsLink) {
        this.team2detailsLink = team2detailsLink;
    }

    public String getTeam2flagLink() {
        return team2flagLink;
    }

    public void setTeam2flagLink(String team2flagLink) {
        this.team2flagLink = team2flagLink;
    }

    public String getTeam2logoLink() {
        return team2logoLink;
    }

    public void setTeam2logoLink(String team2logoLink) {
        this.team2logoLink = team2logoLink;
    }

    public String getTeam2name() {
        return team2name;
    }

    public void setTeam2name(String team2name) {
        this.team2name = team2name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public java.util.List<LiveStream> getStreams() {
        return streams;
    }

    public void setStreams(java.util.List<LiveStream> streams) {
        this.streams = streams;
    }

    public boolean isSection() {
        return isSection;
    }

    public void setSection(boolean section) {
        isSection = section;
    }

    public Date getCestDate() {
        return cestDate;
    }

    public void setCestDate(Date cestDate) {
        this.cestDate = cestDate;
    }

    @Override
    public MatchItem clone() throws CloneNotSupportedException {
        return (MatchItem) super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchItem item = (MatchItem) o;

        if (isSection != item.isSection) return false;
        if (date != null ? !DateUtils.DATE_FORMAT.format(date).equals(DateUtils.DATE_FORMAT.format(item.date)) : item.date != null)
            return false;
        if (link != null ? !link.equals(item.link) : item.link != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return link != null ? link.hashCode() : 0;
    }

    public static enum MatchType {
        FUTURE,
        LIVE,
        POSTPONED,
    }
    public static class List extends ArrayList<MatchItem>{

    }
}
