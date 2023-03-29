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
public class VendorNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>VendorNotFoundException</code> without
     * detail message.
     */
    public VendorNotFoundException() {
    }

    /**
     * Constructs an instance of <code>VendorNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public VendorNotFoundException(String msg) {
        super(msg);
    }
}
