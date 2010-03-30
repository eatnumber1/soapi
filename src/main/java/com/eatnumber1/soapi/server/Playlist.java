package com.eatnumber1.soapi.server;

import java.util.List;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public class Playlist {
    private List<? extends Song> songs;

    public Playlist() {
    }

    public Playlist( List<? extends Song> songs ) {
        this.songs = songs;
    }

    public List<? extends Song> getSongs() {
        return songs;
    }

    public void setSongs( List<? extends Song> songs ) {
        this.songs = songs;
    }
}
