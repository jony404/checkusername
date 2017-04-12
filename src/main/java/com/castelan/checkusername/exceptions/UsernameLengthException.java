/*
 * UsernameLengthException.java
 */
package com.castelan.checkusername.exceptions;

/**
 * Exception for user name length
 * @author Juan Castelan
 */
public class UsernameLengthException extends Exception {

    /**
     * Creates a new instance of <code>UsernameLengthException</code> without
     * detail message.
     */
    public UsernameLengthException() {
    }

    /**
     * Constructs an instance of <code>UsernameLengthException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UsernameLengthException(String msg) {
        super(msg);
    }
}
