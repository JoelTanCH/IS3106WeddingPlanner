/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Request;
import entity.Transaction;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ezekiel Ang
 */
@Stateless
public class TransactionSessionBean implements TransactionSessionBeanLocal {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;

    @Override
    public void createTransaction(Transaction transaction) {
        em.persist(transaction);
//        em.flush();

    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        Transaction trans = em.find(Transaction.class, transactionId);
        return trans;
    }

    @Override
    public Transaction getTransactionByRequestId(Long requestId) {
        Request request = em.find(Request.class, requestId);
        Transaction trans = request.getTransaction();
        return trans;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void persist(Object object) {
        em.persist(object);
    }
}
