/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.WeddingBudgetItem;
import entity.WeddingBudgetList;
import entity.WeddingProject;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidAssociationException;

/**
 *
 * @author Josephine
 */
@Stateless
public class WeddingBudgetSessionBean implements WeddingBudgetSessionBeanLocal {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;

    @Override
    public Long createItem(WeddingBudgetItem budgetItem, Long weddingBudgetListId) throws InvalidAssociationException {
        WeddingBudgetList weddingBudgetList = em.find(WeddingBudgetList.class, weddingBudgetListId);
        if (budgetItem != null && weddingBudgetList != null) {
            em.persist(budgetItem);

            budgetItem.setWeddingBudgetList(weddingBudgetList);
            weddingBudgetList.getWeddingBudgetItems().add(budgetItem);
            em.flush();
            return budgetItem.getWeddingBudgetItemId();
        } else {
            throw new InvalidAssociationException();
        }
    }

//    @Override
//    public void createItem(WeddingBudgetItem budgetItem) {
//        em.persist(budgetItem);
//    }
    @Override
    public List<WeddingBudgetItem> retrieveAllItems() {
        Query itemsQuery = em.createQuery("SELECT item FROM WeddingBudgetItem item");
        List<WeddingBudgetItem> items = itemsQuery.getResultList();
        for (WeddingBudgetItem item : items) {
            if (item.getWeddingBudgetList() != null) {
                item.setWeddingBudgetList(null);
            }
        }
        return items;
    }

    @Override
    public WeddingBudgetItem retrieveItem(Long weddingBudgetItemId) {
        return em.find(WeddingBudgetItem.class, weddingBudgetItemId);
    }

    @Override
    public void updateItem(WeddingBudgetItem weddingBudgetItem) {
        em.merge(weddingBudgetItem);
    }
//    @Override
//    public void updateItem(WeddingBudgetItem weddingBudgetItem) {
//        WeddingBudgetItem item = em.find(WeddingBudgetItem.class, weddingBudgetItem.getWeddingBudgetItemId());
//        if (item != null) {
//            item.setName(weddingBudgetItem.getName());
//            item.setCost(weddingBudgetItem.getCost());
//            item.setIsPaid(weddingBudgetItem.isIsPaid());
//            item.setCategory(weddingBudgetItem.getCategory());
//        }
//    }

    @Override
    public void deleteItem(Long itemId) {
        WeddingBudgetItem item = em.find(WeddingBudgetItem.class, itemId);

        item.setWeddingBudgetList(null);
        item.setRequest(null);

        em.remove(item);
    }

//    @Override
//    public void createBudget(WeddingBudgetList budget) {
//        em.persist(budget);
//    }
    @Override
    public Long createBudget(WeddingBudgetList budget, Long weddingProjectId) throws InvalidAssociationException {
        WeddingProject weddingProject = em.find(WeddingProject.class, weddingProjectId);
        if (budget != null && weddingProject != null) {
            em.persist(budget);
            budget.setWeddingProject(weddingProject);
            em.flush();
            return budget.getWeddingBudgetListId();
        } else {
            throw new InvalidAssociationException();
        }
    }

    @Override
    public List<WeddingBudgetList> getBudgets(Long weddingProjectId) {
        Query query = em.createQuery("SELECT bugdet FROM WeddingBudgetList budget");
        List<WeddingBudgetList> budgets = query.getResultList();
        for (WeddingBudgetList budget : budgets) {
            if (budget.getWeddingProject() != null) {
                budget.setWeddingProject(null);
            }
        }
        return budgets;
    }

    @Override
    public WeddingBudgetList getBudget(Long weddingBudgetListId) {
        WeddingBudgetList budget = em.find(WeddingBudgetList.class, weddingBudgetListId);
        if (budget.getWeddingBudgetItems() != null) {
            budget.setWeddingBudgetItems(null);
        }
        if (budget.getWeddingProject() != null) {
            budget.setWeddingProject(null);
        }
        return budget;
    }

    @Override
    public void updateBudget(WeddingBudgetList budget) {
//        em.merge(budget);
        WeddingBudgetList weddingBudgetList = em.find(WeddingBudgetList.class, budget.getWeddingBudgetListId());
        if (weddingBudgetList != null) {
            weddingBudgetList.setBudget(budget.getBudget());
        }
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
