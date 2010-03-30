package com.eatnumber1.soapi.server;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.AutoCloseInputStream;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public class SongServer {
    public static final String SERVER_PORT_KEY = "soapi.server.port";

    private Playlist playlist;
    private Server server = new Server(Integer.getInteger(SERVER_PORT_KEY, 1024));

    public SongServer() {
    }

    public SongServer( Playlist playlist ) {
        this.playlist = playlist;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist( Playlist playlist ) {
        this.playlist = playlist;
    }

    public void join() throws InterruptedException {
        server.join();
    }

    public void start() throws Exception {
        assert playlist != null;
        server.addHandler(new AbstractHandler() {
            @Override
            public void handle( String s, HttpServletRequest request, HttpServletResponse response, int i ) throws IOException, ServletException {
                OutputStream os = response.getOutputStream();
                try {
                    for( Song song : playlist.getSongs() ) {
                        IOUtils.copy(new AutoCloseInputStream(song.open()), os);
                    }
                } finally {
                    IOUtils.closeQuietly(os);
                }
            }
        });
        server.start();
    }

    public static void main( String[] args ) throws Exception {
        SongServer songServer = new SongServer();
        songServer.setPlaylist(new Playlist(Collections.singletonList(new SongFile(new File("/home/russ/a.mp3")))));
        songServer.start();
    }
}