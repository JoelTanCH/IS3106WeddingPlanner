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
public class InvalidAssociationException extends Exception {

    /**
     * Creates a new instance of <code>InvalidAssociationException</code>
     * without detail message.
     */
    public InvalidAssociationException() {
    }

    /**
     * Constructs an instance of <code>InvalidAssociationException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidAssociationException(String msg) {
        super(msg);
    }
}
