package com.eatnumber1.soapi.controller;

import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public enum SoapLocation {
    SOUTH_VADER("s,The Vator"),
    NORTH_VADER("n,The Vator"),
    SOUTH_STAIRS("s,The Stairs"),
    NORTH_STAIRS("n,The Stairs"),
    THE_L("n,The L");

    @NotNull
    private String key;

    private SoapLocation( @NotNull String key ) {
        this.key = key;
    }

    @NotNull
    public String getKey() {
        return key;
    }
}
