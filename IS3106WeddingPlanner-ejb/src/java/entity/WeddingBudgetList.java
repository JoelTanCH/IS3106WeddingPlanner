/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Ezekiel Ang
 */
@Entity
public class WeddingBudgetList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weddingBudgetListId;
    
    @Column(nullable = false)
    private BigDecimal budget;

    public WeddingBudgetList() {
    }

    @OneToOne(optional = false)
    private WeddingProject weddingProject;

    @OneToMany(mappedBy="weddingBudgetList")
    private List<WeddingBudgetItem> weddingBudgetItems;
    
    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
    
    public Long getWeddingBudgetListId() {
        return weddingBudgetListId;
    }

    public void setWeddingBudgetListId(Long weddingBudgetListId) {
        this.weddingBudgetListId = weddingBudgetListId;
    }


    @Override
    public String toString() {
        return "entity.WeddingBudgetList[ id=" + weddingBudgetListId + " ]";
    }

    /**
     * @return the weddingProject
     */
    public WeddingProject getWeddingProject() {
        return weddingProject;
    }

    /**
     * @param weddingProject the weddingProject to set
     */
    public void setWeddingProject(WeddingProject weddingProject) {
        this.weddingProject = weddingProject;
    }

    /**
     * @return the weddingBudgetItems
     */
    public List<WeddingBudgetItem> getWeddingBudgetItems() {
        return weddingBudgetItems;
    }

    /**
     * @param weddingBudgetItems the weddingBudgetItems to set
     */
    public void setWeddingBudgetItems(List<WeddingBudgetItem> weddingBudgetItems) {
        this.weddingBudgetItems = weddingBudgetItems;
    }
    
}
