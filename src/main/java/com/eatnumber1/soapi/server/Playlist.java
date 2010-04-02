package com.eatnumber1.soapi.server;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public class Playlist {
    @NotNull
    private List<? extends Song> songs;

    public Playlist( @NotNull List<? extends Song> songs ) {
        this.songs = songs;
    }

    @NotNull
    public List<? extends Song> getSongs() {
        return songs;
    }

    public void setSongs( @NotNull List<? extends Song> songs ) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for( Song s : songs ) {
            sb.append(s).append('\n');
        }
        return sb.toString();
    }
}
