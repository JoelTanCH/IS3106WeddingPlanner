/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Transaction;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ezekiel Ang
 */
@Local
public interface TransactionSessionBeanLocal {
    public void createTransaction(Transaction transaction);

    public Transaction getTransactionById(Long transactionId);

    public Transaction getTransactionByRequestId(Long requestId);

    public List<Object[]> getTotalValueGroupedByCategory();
}
