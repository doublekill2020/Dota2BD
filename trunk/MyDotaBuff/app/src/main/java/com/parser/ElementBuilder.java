package com.parser;

import java.net.URI;

/**
 * @author dkuffner
 */
class ElementBuilder {
    private int duration;
    private URI uri;
    private PlaylistInfo playlistInfo;
    private EncryptionInfo encryptionInfo;
    private String title;
    private String quality;
    private long programDate = -1;

    public ElementBuilder() {

    }

    public long programDate() {
        return programDate;
    }

    public ElementBuilder programDate(long programDate) {
        this.programDate = programDate;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ElementBuilder title(String title) {
        this.title = title;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public ElementBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    public URI getUri() {
        return uri;
    }

    public ElementBuilder uri(URI uri) {
        this.uri = uri;
        return this;
    }

    public ElementBuilder playList(final int programId, final int bandWidth, final String codec) {
        this.playlistInfo = new ElementImpl.PlaylistInfoImpl(programId, bandWidth, codec);
        return this;
    }

    public ElementBuilder resetPlatListInfo() {
        playlistInfo = null;
        return this;
    }

    public ElementBuilder resetEncryptedInfo() {
        encryptionInfo = null;
        return this;
    }

    public ElementBuilder reset() {
        duration = 0;
        uri = null;
        title = null;
        programDate = -1;
        resetEncryptedInfo();
        resetPlatListInfo();
        quality = null;
        return this;
    }


    public ElementBuilder encrypted(EncryptionInfo info) {
        this.encryptionInfo = info;
        return this;
    }

    public ElementBuilder encrypted(final URI uri, final String method) {
        encryptionInfo = new ElementImpl.EncryptionInfoImpl(uri, method);
        return this;
    }

    public String getQuality() {
        return quality;
    }

    public ElementBuilder quality(String quality) {
        this.quality = quality;
        return this;
    }

    public Element create() {
        return new ElementImpl(playlistInfo, encryptionInfo, duration, uri, title, programDate, quality);
    }

}
