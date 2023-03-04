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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Ezekiel Ang
 */
@Entity
public class WeddingBudgetItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long weddingBudgetItemId;

    @Column(nullable = false)
    private BigDecimal cost;
    
    @Column(nullable = false)
    private boolean isPaid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryEnum category;

    @OneToOne(optional = false)
    private Request request;

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public boolean isIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    
    
    
    
    public Long getWeddingBudgetItemId() {
        return weddingBudgetItemId;
    }

    public void setWeddingBudgetItemId(Long weddingBudgetItemId) {
        this.weddingBudgetItemId = weddingBudgetItemId;
    }

    @Override
    public String toString() {
        return "entity.WeddingBudgetItem[ id=" + weddingBudgetItemId + " ]";
    }

}
