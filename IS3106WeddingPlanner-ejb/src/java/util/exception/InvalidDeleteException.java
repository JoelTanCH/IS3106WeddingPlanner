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
public class InvalidDeleteException extends Exception {

    /**
     * Creates a new instance of <code>InvalidDeleteException</code> without
     * detail message.
     */
    public InvalidDeleteException() {
    }

    /**
     * Constructs an instance of <code>InvalidDeleteException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidDeleteException(String msg) {
        super(msg);
    }
}
