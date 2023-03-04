/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author leomi
 */
@Entity
public class Guest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String attendingSide;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private int numPax;
    @Column(nullable = false)
    private Boolean rsvp; 
    
    @ManyToOne
    private GuestTable guestTable;
    
    @ManyToOne
    private WeddingProject weddingProject;

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
    
    public Guest(){}
    
    public GuestTable getGuestTable() {
        return guestTable;
    }

    public void setGuestTable(GuestTable guestTable) {
        this.guestTable = guestTable;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttendingSide() {
        return attendingSide;
    }

    public void setAttendingSide(String attendingSide) {
        this.attendingSide = attendingSide;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumPax() {
        return numPax;
    }

    public void setNumPax(int numPax) {
        this.numPax = numPax;
    }

    public Boolean getRsvp() {
        return rsvp;
    }

    public void setRsvp(Boolean rsvp) {
        this.rsvp = rsvp;
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
        if (!(object instanceof Guest)) {
            return false;
        }
        Guest other = (Guest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Guest[ id=" + id + " ]";
    }
    
}
