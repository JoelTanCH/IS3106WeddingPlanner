/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Vendor;
import error.InvalidVendorCategory;
import error.VendorNameNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author joelt
 */
@Local
public interface VendorSessionBeanLocal {
    public List<Vendor> getAllVendors(); 
    public List<Vendor> getVendorByCategory(String vendorCategorySelection) throws InvalidVendorCategory; //ENSURE STRING GIVEN SAME AS ENUMERATION STRING IN FROTNEND
    public Vendor getVendorByVendorName(String vendorName) throws VendorNameNotFoundException;

    public void updateVendor(Vendor v);

    public void createVendor(Vendor v);
    
}
