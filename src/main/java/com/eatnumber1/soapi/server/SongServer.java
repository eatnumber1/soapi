package com.eatnumber1.soapi.server;

import com.eatnumber1.soapi.SoapiConstants;
import com.eatnumber1.util.MessageBundle;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
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

    @NotNull
    public CyclicBarrier start() throws Exception {
        assert playlist != null;
        final CyclicBarrier finishedBarrier = new CyclicBarrier(2);
        server.addHandler(new AbstractHandler() {
            @Override
            public void handle( String s, HttpServletRequest request, HttpServletResponse response, int i ) throws IOException, ServletException {
                log.info(messages.getMessage("com.eatnumber1.soapi.server.playing.begin", request.getRemoteHost(), playlist));
                OutputStream os = response.getOutputStream();
                try {
                    for( Song song : playlist.getSongs() ) {
                        log.info(messages.getMessage("com.eatnumber1.soapi.server.playing.song", song));
                        IOUtils.copy(new AutoCloseInputStream(song.open()), os);
                    }
                } finally {
                    IOUtils.closeQuietly(os);
                }
                log.info(messages.getMessage("com.eatnumber1.soapi.server.playing.done", request.getRemoteHost()));
                try {
                    finishedBarrier.await();
                } catch( InterruptedException e ) {
                    throw new RuntimeException(e);
                } catch( BrokenBarrierException e ) {
                    throw new RuntimeException(e);
                }
            }
        });
        server.start();
        return finishedBarrier;
    }

    public void stop() throws Exception {
        server.stop();
    }
}