/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.WeddingBudgetItem;
import entity.WeddingBudgetList;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Josephine
 */
@Local
public interface WeddingBudgetSessionBeanLocal {

    public void createItem(WeddingBudgetItem budgetItem);

    public List<WeddingBudgetItem> retrieveAllItems();

//    public void deleteItem(Long itemId);

    public void createBudget(WeddingBudgetList budget);

    public void updateBudget(WeddingBudgetList budget);

    public BigDecimal totalCost();

    public BigDecimal totalPaid();
    
}
