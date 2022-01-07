package cn.edu.mydotabuff.model;

import android.support.annotation.NonNull;

import java.util.Date;

public class PlayedWithWrapper implements Comparable<PlayedWithWrapper> {
    public long account_id;
    public long last_played;
    public int win;
    public int games;
    public int with_win;
    public int with_games;
    public int against_win;
    public int against_games;
    public long with_gpm_sum;
    public long with_xpm_sum;
    public String personaname;
    public String name;
    public boolean is_contributor;
    public Date last_login;
    public String avatar;
    public String avatarfull;

    @Override
    public int compareTo(@NonNull PlayedWithWrapper playedWithWrapper) {
        return (int) (playedWithWrapper.last_played - last_played);
    }
}
