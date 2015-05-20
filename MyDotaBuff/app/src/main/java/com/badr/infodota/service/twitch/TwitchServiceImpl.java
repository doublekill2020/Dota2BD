package com.badr.infodota.service.twitch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.api.streams.Stream;
import com.badr.infodota.api.streams.twitch.TwitchAccessToken;
import com.badr.infodota.api.streams.twitch.TwitchChannel;
import com.badr.infodota.api.streams.twitch.TwitchGameStreams;
import com.badr.infodota.api.streams.twitch.TwitchStream;
import com.badr.infodota.api.streams.twitch.TwitchStreamTV;
import com.badr.infodota.dao.DatabaseManager;
import com.badr.infodota.dao.StreamDao;
import com.badr.infodota.remote.twitch.TwitchRemoteService;
import com.parser.Playlist;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 16.05.14
 * Time: 17:58
 */
public class TwitchServiceImpl implements TwitchService {
    private TwitchRemoteService service;
    private StreamDao streamDao;

    @Override
    public TwitchAccessToken getAccessToken(String channelName) {
        return BeanContainer.getInstance().getTwitchRestService().getAccessToken(channelName);
    }

    @Override
    public Stream.List getGameStreams() {
        TwitchGameStreams tgs= BeanContainer.getInstance().getTwitchRestService().getGameStreams();
        if(tgs!=null){
            Stream.List list=new Stream.List();
            for(TwitchStream ts:tgs.getStreams()){
                Stream stream=new Stream();
                stream.setChannel(ts.getChannel().getName());
                stream.setHlsEnabled(true);
                stream.setTitle(ts.getChannel().getStatus());
                stream.setViewers(ts.getViewers());
                stream.setProvider("twitch");
                //stream.setQualities();
                list.add(stream);
            }
            return list;
        }
        return null;
    }

    @Override
    public Stream getStream(String channelName) {
        TwitchStreamTV result= BeanContainer.getInstance().getTwitchRestService().getStream(channelName);
        if(result!=null&&result.getStream()!=null){
            Stream stream= new Stream();
            TwitchChannel channel=result.getStream().getChannel();
            stream.setChannel(channel.getName());
            stream.setTitle(channel.getStatus());
            stream.setViewers(result.getStream().getViewers());
            stream.setProvider("twitch");
            stream.setHlsEnabled(true);
            return stream;
        }
        return null;
    }

    @Override
    public Pair<Playlist, String> getPlaylist(Context context, String channelName, TwitchAccessToken accessToken) {
        try {
            Pair<Playlist, String> result = service.getPlaylist(context, channelName, accessToken);
            if (result.first == null) {
                String message = "Failed to get twitch channel playlist, cause: " + result.second;
                Log.e(TwitchServiceImpl.class.getName(), message);
            }
            return result;
        } catch (Exception e) {
            String message = "Failed to get twitch channel playlist, cause: " + e.getMessage();
            Log.e(TwitchServiceImpl.class.getName(), message, e);
            return Pair.create(null, message);
        }
    }

    @Override
    public boolean isStreamFavourite(Context context, TwitchChannel channel) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            return streamDao.getByName(database, channel.getName()) != null;
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public void addStream(Context context, Stream channel) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            streamDao.saveOrUpdate(database, channel);
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public void deleteStream(Context context, Stream channel) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            streamDao.delete(database, channel);
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public List<Stream> getFavouriteStreams(Context context) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            return streamDao.getAllEntities(database);
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public void initialize() {
        BeanContainer container = BeanContainer.getInstance();
        service = container.getTwitchRemoteService();
        streamDao = container.getStreamDao();
    }
}
