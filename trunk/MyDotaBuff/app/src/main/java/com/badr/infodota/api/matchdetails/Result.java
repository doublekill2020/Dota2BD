package com.badr.infodota.api.matchdetails;

import com.badr.infodota.api.matchhistory.Match;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * User: ABadretdinov
 * Date: 28.08.13
 * Time: 15:19
 */
public class Result extends Match implements Serializable {
    //true if radiant won, false otherwise
    private boolean radiant_win;
    //the total time in seconds the match ran for
    private long duration;
    //an 11-bit unsinged int: see http://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails#Tower_Status
    private long tower_status_radiant;
    //an 11-bit unsinged int: see http://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails#Tower_Status
    private long tower_status_dire;
    //a 6-bit unsinged int: see http://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails#Barracks_Status
    private long barracks_status_radiant;
    //a 6-bit unsinged int: see http://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails#Barracks_Status
    private long barracks_status_dire;
    //for replays
    private long cluster;
    //the time in seconds at which first blood occurred
    private long first_blood_time;
    //the number of human players in the match
    private int human_players;
    //the leauge this match is from (see GetMatchHistory)
    private long leagueid;
    //the number of thumbs up the game has received
    private long positive_votes;
    //the number of thumbs up the game has received
    private long negative_votes;
    /*
    a number representing the game mode of this match
    * '1' : 'All Pick',
    * '2' : "Captains Mode",
    * '3' : 'Random Draft',
    * '4' : 'Single Draft',
    * '5' : 'All Random',
    * '6' : '?? INTRO/DEATH ??',
    * '7' : 'The Diretide',
    * '8' : "Reverse Captains Mode",
    * '9' : 'Greeviling',
    * '10' : 'Tutorial',
    * '11' : 'Mid Only',
    * '12' : 'Least Played',
    * '13' : 'New Player Pool'
    * */
    private long game_mode;
    private String season;

    /*
    * The following fields are only included if there were
    * teams applied to radiant and dire
    * (i.e. this is a league match in a private lobby)
    * */
    //the name of the radiant team
    private String radiant_name;
    private Long radiant_logo;
    private Long radiant_team_id;
    //true if all players on radiant belong to this team, false otherwise (i.e. are the stand-ins {false} or not {true})
    private Integer radiant_team_complete;
    //he name of the dire team
    private String dire_name;
    private Long dire_logo;
    private Long dire_team_id;
    //true if all players on dire belong to this team, false otherwise (i.e. are the stand-ins {false} or not {true})
    private Integer dire_team_complete;

    private List<PickBan> picks_bans;


    private boolean section = false;

    public Result() {
        super();
    }

    public boolean isSection() {
        return section;
    }

    public void setSection(boolean section) {
        this.section = section;
    }

    public boolean isRadiant_win() {
        return radiant_win;
    }

    public void setRadiant_win(boolean radiant_win) {
        this.radiant_win = radiant_win;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getTower_status_radiant() {
        return tower_status_radiant;
    }

    public void setTower_status_radiant(long tower_status_radiant) {
        this.tower_status_radiant = tower_status_radiant;
    }

    public long getTower_status_dire() {
        return tower_status_dire;
    }

    public void setTower_status_dire(long tower_status_dire) {
        this.tower_status_dire = tower_status_dire;
    }

    public long getBarracks_status_radiant() {
        return barracks_status_radiant;
    }

    public void setBarracks_status_radiant(long barracks_status_radiant) {
        this.barracks_status_radiant = barracks_status_radiant;
    }

    public long getBarracks_status_dire() {
        return barracks_status_dire;
    }

    public void setBarracks_status_dire(long barracks_status_dire) {
        this.barracks_status_dire = barracks_status_dire;
    }

    public long getCluster() {
        return cluster;
    }

    public void setCluster(long cluster) {
        this.cluster = cluster;
    }

    public long getFirst_blood_time() {
        return first_blood_time;
    }

    public void setFirst_blood_time(long first_blood_time) {
        this.first_blood_time = first_blood_time;
    }

    public int getHuman_players() {
        return human_players;
    }

    public void setHuman_players(int human_players) {
        this.human_players = human_players;
    }

    public long getLeagueid() {
        return leagueid;
    }

    public void setLeagueid(long leagueid) {
        this.leagueid = leagueid;
    }

    public long getPositive_votes() {
        return positive_votes;
    }

    public void setPositive_votes(long positive_votes) {
        this.positive_votes = positive_votes;
    }

    public long getNegative_votes() {
        return negative_votes;
    }

    public void setNegative_votes(long negative_votes) {
        this.negative_votes = negative_votes;
    }

    public long getGame_mode() {
        return game_mode;
    }

    public void setGame_mode(long game_mode) {
        this.game_mode = game_mode;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getRadiant_name() {
        return radiant_name;
    }

    public void setRadiant_name(String radiant_name) {
        this.radiant_name = radiant_name;
    }

    public Long getRadiant_logo() {
        return radiant_logo;
    }

    public void setRadiant_logo(Long radiant_logo) {
        this.radiant_logo = radiant_logo;
    }

    public Integer getRadiant_team_complete() {
        return radiant_team_complete;
    }

    public void setRadiant_team_complete(Integer radiant_team_complete) {
        this.radiant_team_complete = radiant_team_complete;
    }

    public String getDire_name() {
        return dire_name;
    }

    public void setDire_name(String dire_name) {
        this.dire_name = dire_name;
    }

    public Long getDire_logo() {
        return dire_logo;
    }

    public void setDire_logo(Long dire_logo) {
        this.dire_logo = dire_logo;
    }

    public Integer getDire_team_complete() {
        return dire_team_complete;
    }

    public void setDire_team_complete(Integer dire_team_complete) {
        this.dire_team_complete = dire_team_complete;
    }

    public List<PickBan> getPicks_bans() {
        return picks_bans;
    }

    public void setPicks_bans(List<PickBan> picks_bans) {
        this.picks_bans = picks_bans;
    }

    public Long getRadiant_team_id() {
        return radiant_team_id;
    }

    public void setRadiant_team_id(Long radiant_team_id) {
        this.radiant_team_id = radiant_team_id;
    }

    public Long getDire_team_id() {
        return dire_team_id;
    }

    public void setDire_team_id(Long dire_team_id) {
        this.dire_team_id = dire_team_id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        if (section != result.section) return false;
        if (section) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();
            dateFormat.setTimeZone(tz);
            long rightTimestamp = result.getStart_time() * 1000;
            long leftTimestamp = getStart_time() * 1000;
            if (!dateFormat.format(new Date(rightTimestamp)).equals(dateFormat.format(new Date(leftTimestamp)))) {
                return false;
            }
            /*rightTimestamp/=3600; //hours
            leftTimestamp/=3600;
            rightTimestamp/=24; //days
            leftTimestamp/=24;
            if(rightTimestamp!=leftTimestamp)return false;*/
        } else {
            if (barracks_status_dire != result.barracks_status_dire) return false;
            if (barracks_status_radiant != result.barracks_status_radiant) return false;
            if (cluster != result.cluster) return false;
            if (duration != result.duration) return false;
            if (first_blood_time != result.first_blood_time) return false;
            if (game_mode != result.game_mode) return false;
            if (human_players != result.human_players) return false;
            if (leagueid != result.leagueid) return false;
            if (negative_votes != result.negative_votes) return false;
            if (positive_votes != result.positive_votes) return false;
            if (radiant_win != result.radiant_win) return false;
            if (tower_status_dire != result.tower_status_dire) return false;
            if (tower_status_radiant != result.tower_status_radiant) return false;
            if (dire_logo != null ? !dire_logo.equals(result.dire_logo) : result.dire_logo != null)
                return false;
            if (dire_name != null ? !dire_name.equals(result.dire_name) : result.dire_name != null)
                return false;
            if (dire_team_complete != null ? !dire_team_complete.equals(result.dire_team_complete) : result.dire_team_complete != null)
                return false;
            if (dire_team_id != null ? !dire_team_id.equals(result.dire_team_id) : result.dire_team_id != null)
                return false;
            if (picks_bans != null ? !picks_bans.equals(result.picks_bans) : result.picks_bans != null)
                return false;
            if (radiant_logo != null ? !radiant_logo.equals(result.radiant_logo) : result.radiant_logo != null)
                return false;
            if (radiant_name != null ? !radiant_name.equals(result.radiant_name) : result.radiant_name != null)
                return false;
            if (radiant_team_complete != null ? !radiant_team_complete.equals(result.radiant_team_complete) : result.radiant_team_complete != null)
                return false;
            if (radiant_team_id != null ? !radiant_team_id.equals(result.radiant_team_id) : result.radiant_team_id != null)
                return false;
            if (season != null ? !season.equals(result.season) : result.season != null)
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (radiant_win ? 1 : 0);
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        result = 31 * result + (int) (tower_status_radiant ^ (tower_status_radiant >>> 32));
        result = 31 * result + (int) (tower_status_dire ^ (tower_status_dire >>> 32));
        result = 31 * result + (int) (barracks_status_radiant ^ (barracks_status_radiant >>> 32));
        result = 31 * result + (int) (barracks_status_dire ^ (barracks_status_dire >>> 32));
        result = 31 * result + (int) (cluster ^ (cluster >>> 32));
        result = 31 * result + (int) (first_blood_time ^ (first_blood_time >>> 32));
        result = 31 * result + human_players;
        result = 31 * result + (int) (leagueid ^ (leagueid >>> 32));
        result = 31 * result + (int) (positive_votes ^ (positive_votes >>> 32));
        result = 31 * result + (int) (negative_votes ^ (negative_votes >>> 32));
        result = 31 * result + (int) (game_mode ^ (game_mode >>> 32));
        result = 31 * result + (season != null ? season.hashCode() : 0);
        result = 31 * result + (radiant_name != null ? radiant_name.hashCode() : 0);
        result = 31 * result + (radiant_logo != null ? radiant_logo.hashCode() : 0);
        result = 31 * result + (radiant_team_id != null ? radiant_team_id.hashCode() : 0);
        result = 31 * result + (radiant_team_complete != null ? radiant_team_complete.hashCode() : 0);
        result = 31 * result + (dire_name != null ? dire_name.hashCode() : 0);
        result = 31 * result + (dire_logo != null ? dire_logo.hashCode() : 0);
        result = 31 * result + (dire_team_id != null ? dire_team_id.hashCode() : 0);
        result = 31 * result + (dire_team_complete != null ? dire_team_complete.hashCode() : 0);
        result = 31 * result + (picks_bans != null ? picks_bans.hashCode() : 0);
        return result;
    }
}
