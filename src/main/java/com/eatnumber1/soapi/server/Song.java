package com.eatnumber1.soapi.server;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public interface Song {
    @NotNull
    InputStream open() throws FileNotFoundException;
}
