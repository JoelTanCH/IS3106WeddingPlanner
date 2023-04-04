/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author leomi
 */
@Entity
public class Stage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stageId;
    @Column(nullable = false)
    private int tableNumber;
    @Column(nullable = false)
    private double locationX;
    @Column(nullable = false)
    private double locationY;
    @Column(nullable = false)
    private double stageHeight;
    @Column(nullable = false)
    private double stageWidth;
    
    @ManyToOne
    private WeddingProject weddingProject;

    public Stage() {
    }
    /**
     * @return the weddingProject
     */
    public WeddingProject getWeddingProject() {
        return weddingProject;
    }

    
    public double getStageHeight() {
        return stageHeight;
    }

    public void setStageHeight(double stageHeight) {
        this.stageHeight = stageHeight;
    }

    public double getStageWidth() {
        return stageWidth;
    }

    /**
     * @param weddingProject the weddingProject to set
     */
    public void setStageWidth(double stageWidth) {    
        this.stageWidth = stageWidth;
    }

    public void setWeddingProject(WeddingProject weddingProject) {
        this.weddingProject = weddingProject;
    }
    

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    
    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stageId != null ? stageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the stageId fields are not set
        if (!(object instanceof GuestTable)) {
            return false;
        }
        Stage other = (Stage) object;
        if ((this.stageId == null && other.stageId != null) || (this.stageId != null && !this.stageId.equals(other.stageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Table[ id=" + stageId + " ]";
    }
    
}