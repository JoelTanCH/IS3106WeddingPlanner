/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class WeddingChecklist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weddingCheckListId;
    
//    @OneToOne(optional = false)
//    private WeddingProject weddingProject;
    
    @OneToMany(mappedBy = "weddingChecklist")
    private List<WeddingTask> weddingTasks;
    
    public WeddingChecklist(){
        weddingTasks = new ArrayList<WeddingTask>();
    }

    public List<WeddingTask> getWeddingTasks() {
        return weddingTasks;
    }

    public void setWeddingTasks(List<WeddingTask> weddingTasks) {
        this.weddingTasks = weddingTasks;
    }
    
    
    
    
    public Long getWeddingCheckListId() {
        return weddingCheckListId;
    }

    public void setWeddingCheckListId(Long weddingCheckListId) {
        this.weddingCheckListId = weddingCheckListId;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the weddingCheckListId fields are not set
        if (!(object instanceof WeddingChecklist)) {
            return false;
        }
        WeddingChecklist other = (WeddingChecklist) object;
        if ((this.weddingCheckListId == null && other.weddingCheckListId != null) || (this.weddingCheckListId != null && !this.weddingCheckListId.equals(other.weddingCheckListId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.WeddingChecklist[ id=" + weddingCheckListId + " ]";
    }
    
}
