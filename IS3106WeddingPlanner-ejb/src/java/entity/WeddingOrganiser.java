/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author PERSONAL
 */
@Entity
public class WeddingOrganiser extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @OneToMany(mappedBy="weddingOrganiser")
    private List<WeddingProject> weddingProjects;
    

    /**
     * @return the weddingProjects
     */
    public List<WeddingProject> getWeddingProjects() {
        return weddingProjects;
    }

    /**
     * @param weddingProjects the weddingProjects to set
     */
    public void setWeddingProjects(List<WeddingProject> weddingProjects) {
        this.weddingProjects = weddingProjects;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the userId fields are not set
        if (!(object instanceof WeddingOrganiser)) {
            return false;
        }
        WeddingOrganiser other = (WeddingOrganiser) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.WeddingOrganiser[ id=" + userId + " ]";
    }
    
}
