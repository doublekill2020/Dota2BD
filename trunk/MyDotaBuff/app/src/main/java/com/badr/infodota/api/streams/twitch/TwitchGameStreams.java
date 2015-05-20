package com.badr.infodota.api.streams.twitch;

import java.util.Map;

/**
 * User: Histler
 * Date: 25.02.14
 */
public class TwitchGameStreams {
    private Map<String, String> _links;
    private TwitchStream.List streams;

    public TwitchGameStreams() {
    }

    public Map<String, String> get_links() {
        return _links;
    }

    public void set_links(Map<String, String> _links) {
        this._links = _links;
    }

    public TwitchStream.List getStreams() {
        return streams;
    }

    public void setStreams(TwitchStream.List streams) {
        this.streams = streams;
    }
}
