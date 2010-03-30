package com.eatnumber1.soapi.controller;

import com.eatnumber1.util.MessageBundle;
import org.apache.commons.httpclient.HttpStatus;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public class HttpException extends Exception {
    private static MessageBundle messages = MessageBundle.getMessageBundle(HttpException.class);

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
        sb.append(messages.getMessage("com.eatnumber1.soapi.controller.error.http", code, HttpStatus.getStatusText(code)));
        sb.append("\n").append(super.toString());
        return sb.toString();
    }
}
