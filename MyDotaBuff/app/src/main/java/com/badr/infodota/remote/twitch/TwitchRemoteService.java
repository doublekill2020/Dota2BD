package com.badr.infodota.remote.twitch;

import android.content.Context;
import android.util.Pair;

import com.badr.infodota.api.streams.twitch.TwitchAccessToken;
import com.badr.infodota.remote.BaseRemoteService;
import com.parser.Playlist;

/**
 * User: ABadretdinov
 * Date: 16.05.14
 * Time: 17:37
 */
public interface TwitchRemoteService extends BaseRemoteService {
    TwitchAccessToken getAccessToken(Context context, String channelName) throws Exception;

    Pair<Playlist, String> getPlaylist(Context context, String channelName, TwitchAccessToken accessToken) throws Exception;

    String getPlaylistUrl(Context context, String channelName, TwitchAccessToken accessToken);
}
