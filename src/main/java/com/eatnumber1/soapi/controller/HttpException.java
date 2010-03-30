package com.eatnumber1.soapi.controller;

import org.apache.commons.httpclient.HttpStatus;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public class HttpException extends Exception {
    private int code;

    public HttpException() {
    }

    public HttpException( String message ) {
        super(message);
    }

    public HttpException( int code ) {
        this.code = code;
    }

    public HttpException( String message, int code ) {
        super(message);
        this.code = code;
    }

    public void setCode( int code ) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP Error (").append(code).append("): ");
        sb.append(HttpStatus.getStatusText(code)).append("\n");
        sb.append(super.toString());
        return sb.toString();
    }
}
