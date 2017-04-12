/*
 * RestrictedWordException.java
 */
package com.castelan.checkusername.exceptions;

/**
 * Exception for restricted words
 * @author Juan Castelan
 */
public class RestrictedWordException extends Exception {

    /**
     * Creates a new instance of <code>RestrictedWordException</code> without
     * detail message.
     */
    public RestrictedWordException() {
    }

    /**
     * Constructs an instance of <code>RestrictedWordException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public RestrictedWordException(String msg) {
        super(msg);
    }
    
    /**
     * Constructs an instance of <code>RestrictedWordException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     * @param ex the exception object.
     */
    public RestrictedWordException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
