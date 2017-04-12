/*
 * DaoException.java
 * 
 * Created on April, 2017
 */
package com.castelan.checkusername.exceptions;

/**
 * Exception for the system layer
 * @author Juan Castelan
 */
public class DaoException extends Exception {

    /**
     * Creates a new instance of <code>DaoException</code> without detail
     * message.
     */
    public DaoException() {
    }

    /**
     * Constructs an instance of <code>DaoException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public DaoException(String msg) {
        super(msg);
    }
    
    /**
     * Constructs an instance of <code>DaoException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     * @param ex the exception object.
     */
    public DaoException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
