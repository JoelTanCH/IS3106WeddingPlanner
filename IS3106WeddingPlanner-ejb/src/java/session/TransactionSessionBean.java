/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Request;
import entity.Transaction;
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

    @Override
    public List<Object[]> getTotalValueGroupedByCategory() {
//        Query query = em.createQuery("SELECT v.category, SUM(t.totalPrice) FROM Transaction t JOIN Request r JOIN Vendor v WHERE t.isPaid = true "
//                + "GROUP BY v.category");
        Query query = em.createQuery("SELECT v.category, SUM(t.totalPrice) FROM Transaction t JOIN t.request r JOIN r.vendor v "
                + "WHERE t.isPaid = TRUE GROUP BY v.category");

        return query.getResultList();
        // so Object[] here consists of a String and then a BigDecimal...?
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void persist(Object object) {
        em.persist(object);
    }
}
