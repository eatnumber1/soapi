package com.eatnumber1.soapi.server;

import com.eatnumber1.soapi.SoapiConstants;
import com.eatnumber1.util.MessageBundle;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.AutoCloseInputStream;
import org.jetbrains.annotations.NotNull;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public class SongServer {
    @NotNull
    private static final Logger log = LoggerFactory.getLogger(SongServer.class);

    @NotNull
    private static final MessageBundle messages = MessageBundle.getMessageBundle(SongServer.class);

    private Playlist playlist;
    private final Server server = new Server(SoapiConstants.getLocalPort());
    private final Lock serverRunningLock = new ReentrantLock();

    {
        serverRunningLock.lock();
    }

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

    public void waitForStart() throws InterruptedException {
        if( server.isStarted() ) return;
        synchronized( server ) {
            server.wait();
        }
    }

    public void start() throws Exception {
        assert playlist != null;
        server.addHandler(new AbstractHandler() {
            @Override
            protected void doStart() throws Exception {
                super.doStart();
                synchronized( server ) {
                    server.notifyAll();
                }
            }

            @Override
            public void handle( String s, HttpServletRequest request, HttpServletResponse response, int i ) throws IOException, ServletException {
                log.info(messages.getMessage("com.eatnumber1.soapi.server.connected", request.getRemoteHost()));
                OutputStream os = response.getOutputStream();
                try {
                    for( Song song : playlist.getSongs() ) {
                        IOUtils.copy(new AutoCloseInputStream(song.open()), os);
                    }
                } finally {
                    IOUtils.closeQuietly(os);
                }
                log.info(messages.getMessage("com.eatnumber1.soapi.server.done", request.getRemoteHost()));
                try {
                    server.stop();
                } catch( Exception e ) {
                    throw new RuntimeException(e);
                }
            }
        });
        server.start();
    }
}