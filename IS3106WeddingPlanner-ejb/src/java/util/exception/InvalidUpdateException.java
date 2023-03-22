/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author leomi
 */
public class InvalidUpdateException extends Exception {

    /**
     * Creates a new instance of <code>InvalidUpdateException</code> without
     * detail message.
     */
    public InvalidUpdateException() {
    }

    /**
     * Constructs an instance of <code>InvalidUpdateException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidUpdateException(String msg) {
        super(msg);
    }
}
