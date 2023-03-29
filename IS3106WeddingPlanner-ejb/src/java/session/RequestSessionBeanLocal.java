/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Request;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ezekiel Ang
 */
@Local
public interface RequestSessionBeanLocal {

    public Request retrieveRequest(Long requestId);

    public void createRequest(Request request);
    
    public List<Request> retrieveVendorRequests(Long vendorId);

    public void acceptRequest(Long requestId);
    
    public void rejectRequest(Long requestId);
}
