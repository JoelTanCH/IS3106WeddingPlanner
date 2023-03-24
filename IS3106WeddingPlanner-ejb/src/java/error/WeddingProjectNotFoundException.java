/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

/**
 *
 * @author PERSONAL
 */
public class WeddingProjectNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>WeddingProjectNotFoundException</code>
     * without detail message.
     */
    public WeddingProjectNotFoundException() {
    }

    /**
     * Constructs an instance of <code>WeddingProjectNotFoundException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public WeddingProjectNotFoundException(String msg) {
        super(msg);
    }
}
