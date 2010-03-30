package com.eatnumber1.soapi.controller;

import com.eatnumber1.util.MessageBundle;
import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public enum SoapResponse {
    OK("OK");

    @NotNull
    private static MessageBundle messages = MessageBundle.getMessageBundle(SoapResponse.class);

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
        throw new UnsupportedOperationException(messages.getMessage("com.eatnumber1.soapi.controller.error.response-unsupported", v));
    }
}
