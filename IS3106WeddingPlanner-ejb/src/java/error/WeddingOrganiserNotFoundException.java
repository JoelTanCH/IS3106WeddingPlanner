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
public class WeddingOrganiserNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>WeddingOrganiserNotFoundException</code>
     * without detail message.
     */
    public WeddingOrganiserNotFoundException() {
    }

    /**
     * Constructs an instance of <code>WeddingOrganiserNotFoundException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public WeddingOrganiserNotFoundException(String msg) {
        super(msg);
    }
}
