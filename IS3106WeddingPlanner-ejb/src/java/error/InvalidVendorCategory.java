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
public class InvalidVendorCategory extends Exception {

    /**
     * Creates a new instance of <code>InvalidVendorCategory</code> without
     * detail message.
     */
    public InvalidVendorCategory() {
    }

    /**
     * Constructs an instance of <code>InvalidVendorCategory</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidVendorCategory(String msg) {
        super(msg);
    }
}
