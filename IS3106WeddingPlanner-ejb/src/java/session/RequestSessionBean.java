/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Request;
import entity.Transaction;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
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

    @EJB
    private TransactionSessionBeanLocal transactionSessionBeanLocal;

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

    @Override
    public void setNewRequestPrice(Long requestId, BigDecimal newPrice) {
        Request request = em.find(Request.class, requestId);
        request.setQuotedPrice(newPrice);
        Transaction newTransaction = new Transaction();
        newTransaction.setIsPaid(false);
        newTransaction.setTotalPrice(newPrice);
        newTransaction.setTransactionTime(new Date());
        newTransaction.setRequest(request);
        transactionSessionBeanLocal.createTransaction(newTransaction);
        request.setTransaction(newTransaction);
        em.flush();

    }

    @Override
    public void payRequest(Long requestId) {
        Request request = em.find(Request.class, requestId);
        request.getTransaction().setIsPaid(true);
        em.flush();

    }

    @Override
    public Long checkSchedule(Long vendorId, Long requestId) {
        Request request = em.find(Request.class, requestId);
        Date newRequestDate = request.getRequestDate();
        String query = "SELECT COUNT(req) FROM Request req WHERE req.vendor.userId = ?1 AND req.isAccepted = TRUE AND FUNCTION('DATE', req.requestDate) = FUNCTION('DATE', ?2)";
        Query result = em.createQuery(query).setParameter(1, vendorId).setParameter(2, newRequestDate);
        Long numberOfClashes = (Long) result.getSingleResult();

        return numberOfClashes;

    }

}
