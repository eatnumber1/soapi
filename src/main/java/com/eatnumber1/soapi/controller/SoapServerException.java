package com.eatnumber1.soapi.controller;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public class SoapServerException extends Exception {
    private SoapResponse response;

    public SoapServerException( SoapResponse response ) {
        this.response = response;
    }

    public SoapServerException( String message, SoapResponse response ) {
        super(message);
        this.response = response;
    }

    public SoapServerException( String message, Throwable cause, SoapResponse response ) {
        super(message, cause);
        this.response = response;
    }

    public SoapServerException( Throwable cause, SoapResponse response ) {
        super(cause);
        this.response = response;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(response);
        String message = super.getMessage();
        if( !message.isEmpty() ) sb.append(": ").append(super.getMessage());
        return sb.toString();
    }
}
