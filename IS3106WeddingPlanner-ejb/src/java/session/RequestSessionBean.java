/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Request;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ezekiel Ang
 */
@Stateless
public class RequestSessionBean implements RequestSessionBeanLocal {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;

    @Override
    public Request retrieveRequest(Long requestId) {
        String query = "SELECT req FROM Request req WHERE req.requestId = ?1";
        Query result = em.createQuery(query).setParameter(1, requestId);
        Request req = (Request) result.getSingleResult();
        return req;
    }

    @Override
    public void createRequest(Request request) {
        em.persist(request);
    }

    @Override
    public List<Request> retrieveVendorRequests(Long vendorId) {
        String query = "SELECT req FROM Request req WHERE req.vendor.userId = ?1";
        Query result = em.createQuery(query).setParameter(1, vendorId);
        List<Request> req = (List<Request>) result.getResultList();
        return req;
    }

    @Override
    public void acceptRequest(Long requestId) {
        Request request = em.find(Request.class, requestId);
        request.setIsAccepted(true);
        em.flush();
    }

    @Override
    public void rejectRequest(Long requestId) {
        Request request = em.find(Request.class, requestId);
        request.setIsAccepted(false);
        em.flush();
    }

}
