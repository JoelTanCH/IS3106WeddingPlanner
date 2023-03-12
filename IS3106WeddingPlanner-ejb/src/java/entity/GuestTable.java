/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class GuestTable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int tableNumber;
    @Column(nullable = false)
    private double locationX;
    @Column(nullable = false)
    private double locationY;
    @Column(nullable = false)
    private double tableSize;
    @Column(nullable = false)
    private int capacity;
    @Column(nullable = false)
    private int currOccupancy;
    
    
    @OneToMany(mappedBy = "guestTable")
    private List<Guest> guests;

    @ManyToOne(optional = false)
    private WeddingProject weddingProject;

    public GuestTable() {
        this.guests = new ArrayList<>();
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
    
    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public double getTableSize() {
        return tableSize;
    }

    public void setTableSize(double tableSize) {
        this.tableSize = tableSize;
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


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrOccupancy() {
        return currOccupancy;
    }

    public void setCurrOccupancy(int currOccupancy) {
        this.currOccupancy = currOccupancy;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GuestTable)) {
            return false;
        }
        GuestTable other = (GuestTable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Table[ id=" + id + " ]";
    }
    
}
