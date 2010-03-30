package com.eatnumber1.soapi.controller;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public class QueueFullException extends Exception {
    public QueueFullException() {
    }

    public QueueFullException( String message ) {
        super(message);
    }

    public QueueFullException( String message, Throwable cause ) {
        super(message, cause);
    }

    public QueueFullException( Throwable cause ) {
        super(cause);
    }
}
