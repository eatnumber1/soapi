package com.eatnumber1.soapi.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public class SongFile implements Song {
    @NotNull
    private File file;

    public SongFile( @NotNull File file ) {
        this.file = file;
    }

    @NotNull
    @Override
    public InputStream open() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    @Override
    public String toString() {
        return file.toString();
    }
}
