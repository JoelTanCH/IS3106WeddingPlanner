/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
 * @author TUFGAMING
 */

@Entity
public class WeddingParentTask implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weddingParentTaskId;
    
    @Column(nullable=false)
    private String taskDescription;
    
    @Column(nullable=false)
    private boolean isDone;
    
    @ManyToOne
    private WeddingChecklist weddingChecklist;

    @OneToMany(mappedBy="weddingParentTask")
    private List<WeddingSubtask> weddingSubtasks;
    
    public WeddingParentTask() {
        
    }
    
    public Long getWeddingParentTaskId() {
        return weddingParentTaskId;
    }

    public void setWeddingParentTaskId(Long weddingParentTaskId) {
        this.weddingParentTaskId = weddingParentTaskId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (weddingParentTaskId != null ? weddingParentTaskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the weddingParentTaskId fields are not set
        if (!(object instanceof WeddingParentTask)) {
            return false;
        }
        WeddingParentTask other = (WeddingParentTask) object;
        if ((this.weddingParentTaskId == null && other.weddingParentTaskId != null) || (this.weddingParentTaskId != null && !this.weddingParentTaskId.equals(other.weddingParentTaskId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.WeddingParentTask[ id=" + weddingParentTaskId + " ]";
    }

    /**
     * @return the taskDescription
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    /**
     * @param taskDescription the taskDescription to set
     */
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    /**
     * @return the weddingChecklist
     */
    public WeddingChecklist getWeddingChecklist() {
        return weddingChecklist;
    }

    /**
     * @param weddingChecklist the weddingChecklist to set
     */
    public void setWeddingChecklist(WeddingChecklist weddingChecklist) {
        this.weddingChecklist = weddingChecklist;
    }

    /**
     * @return the isDone
     */
    public boolean isIsDone() {
        return isDone;
    }

    /**
     * @param isDone the isDone to set
     */
    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * @return the weddingSubtasks
     */
    public List<WeddingSubtask> getWeddingSubtasks() {
        return weddingSubtasks;
    }

    /**
     * @param weddingSubtasks the weddingSubtasks to set
     */
    public void setWeddingSubtasks(List<WeddingSubtask> weddingSubtasks) {
        this.weddingSubtasks = weddingSubtasks;
    }
    
}
