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
public class InvalidGetException extends Exception {

    /**
     * Creates a new instance of <code>InvalidGetException</code> without detail
     * message.
     */
    public InvalidGetException() {
    }

    /**
     * Constructs an instance of <code>InvalidGetException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidGetException(String msg) {
        super(msg);
    }
}
