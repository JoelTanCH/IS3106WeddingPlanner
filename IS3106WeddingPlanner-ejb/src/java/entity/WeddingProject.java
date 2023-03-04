/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Josephine
 */
@Entity
public class WeddingProject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long weddingProjectId;

    public Long getWeddingProjectId() {
        return weddingProjectId;
    }

    public void setWeddingProjectId(Long weddingProjectId) {
        this.weddingProjectId = weddingProjectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (weddingProjectId != null ? weddingProjectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the weddingProjectId fields are not set
        if (!(object instanceof WeddingProject)) {
            return false;
        }
        WeddingProject other = (WeddingProject) object;
        if ((this.weddingProjectId == null && other.weddingProjectId != null) || (this.weddingProjectId != null && !this.weddingProjectId.equals(other.weddingProjectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.WeddingProject[ id=" + weddingProjectId + " ]";
    }
    
}
