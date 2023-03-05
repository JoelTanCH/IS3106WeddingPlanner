/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

//    @OneToOne(optional = false)
//    private WeddingProject weddingProject;

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
    
}
