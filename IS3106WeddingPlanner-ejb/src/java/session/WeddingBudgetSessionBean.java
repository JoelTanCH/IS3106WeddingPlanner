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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Josephine
 */
@Stateless
public class WeddingBudgetSessionBean implements WeddingBudgetSessionBeanLocal {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;
    
    @Override
    public void createItem(WeddingBudgetItem budgetItem) {
        em.persist(budgetItem);
    }
    
    @Override
    public List<WeddingBudgetItem> retrieveAllItems() {
        Query itemsQuery = em.createQuery("SELECT item FROM WeddingBudgetItem item");
        return itemsQuery.getResultList();
    }
    
    @Override
    public void deleteItem(Long itemId) {
        WeddingBudgetItem item = em.find(WeddingBudgetItem.class, itemId);
        
        item.setWeddingBudgetList(null);
        item.setRequest(null);
        
        em.remove(item);
    }
    
    @Override
    public void createBudget(WeddingBudgetList budget) {
        em.persist(budget);
    }
    
    @Override
    public void updateBudget(WeddingBudgetList budget) {
        em.merge(budget);
    }
    
    @Override
    public BigDecimal totalCost() {
        BigDecimal totalCost = BigDecimal.valueOf(0);
        List<WeddingBudgetItem> items = retrieveAllItems();
        
        for (WeddingBudgetItem item : items) {
            totalCost.add(item.getCost());
        }
        
        return totalCost;
    }
    
    @Override
    public BigDecimal totalPaid() {
        BigDecimal totalPaid = BigDecimal.valueOf(0);
        
        Query query = em.createQuery("SELECT item FROM WeddingBudgetItem item"
                + "WHERE item.isPaid = true");
        List<WeddingBudgetItem> paidItems = query.getResultList();
        
        for (WeddingBudgetItem item : paidItems) {
            totalPaid.add(item.getCost());
        }
        
        return totalPaid;
    }
}
