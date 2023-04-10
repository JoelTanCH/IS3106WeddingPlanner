/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Request;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ezekiel Ang
 */
@Local
public interface RequestSessionBeanLocal {

    public Request retrieveRequest(Long requestId);

    public Request createRequest(Request request);
    
    public List<Request> retrieveVendorRequests(Long vendorId);
    
     public List<Request> retrieveAcceptedVendorRequests(Long vendorId);

    public void acceptRequest(Long requestId);
    
    public void rejectRequest(Long requestId);
    
    public void setNewRequestPrice(Long requestId, BigDecimal newPrice);
    
    public void payRequest(Long requestId);
    
    public Long checkSchedule(Long vendorId, Long requestId);
    
    public void createRequestFromFrontend(Request request, Long weddingProjectId, Long vendorId);

    public boolean checkIfRequestExists(Long projId, Long vendorId);
    public List<Request> getAllRequests();
}
