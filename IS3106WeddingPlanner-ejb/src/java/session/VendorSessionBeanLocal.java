/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Vendor;
import error.InvalidVendorCategory;
import error.VendorNameNotFoundException;
import error.VendorNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author joelt
 */
@Local
public interface VendorSessionBeanLocal {
    public Vendor getVendor(Long vId) throws VendorNotFoundException;

    public List<Vendor> getAllVendors();

    public List<Vendor> getVendorByCategory(String vendorCategorySelection) throws InvalidVendorCategory; //ENSURE STRING GIVEN SAME AS ENUMERATION STRING IN FROTNEND

    public Vendor getVendorByVendorName(String vendorName) throws VendorNameNotFoundException;
    
    public Long createVendor(Vendor vendor);

    public List<Vendor> getVendorsByUsernamePassword(String username, String password);

    public Vendor getVendorById(Long vId) throws VendorNotFoundException;

    public void updateVendor(Vendor v) throws VendorNotFoundException;
    public Vendor getVendorByRequestId(Long requestId);

}
