/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

/**
 *
 * @author joelt
 */
public class VendorNameNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>VendorNameNotFoundException</code>
     * without detail message.
     */
    public VendorNameNotFoundException() {
    }

    /**
     * Constructs an instance of <code>VendorNameNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public VendorNameNotFoundException(String msg) {
        super(msg);
    }
}
