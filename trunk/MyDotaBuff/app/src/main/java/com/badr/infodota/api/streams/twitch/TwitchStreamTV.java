package com.badr.infodota.api.streams.twitch;

import java.util.Map;

/**
 * User: Histler
 * Date: 25.02.14
 */
public class TwitchStreamTV {
    private Map<String, String> _links;
    private TwitchStream stream;

    public TwitchStreamTV() {
    }

    public Map<String, String> get_links() {
        return _links;
    }

    public void set_links(Map<String, String> _links) {
        this._links = _links;
    }

    public TwitchStream getStream() {
        return stream;
    }

    public void setStream(TwitchStream stream) {
        this.stream = stream;
    }
}
