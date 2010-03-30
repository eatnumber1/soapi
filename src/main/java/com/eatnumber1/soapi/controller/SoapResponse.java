package com.eatnumber1.soapi.controller;

import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public enum SoapResponse {
    OK("OK");

    @NotNull
    private String response;

    private SoapResponse( @NotNull String response ) {
        this.response = response;
    }

    @NotNull
    public static SoapResponse create( @NotNull String v ) {
        for( SoapResponse r : values() ) {
            if( r.response.equals(v) ) return r;
        }
        throw new UnsupportedOperationException("Unsupported server response" + v);
    }
}
