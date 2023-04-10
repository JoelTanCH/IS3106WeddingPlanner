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
 * @author TUFGAMING
 */

@Entity
public class WeddingSubtask implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weddingSubtaskId;

    @Column(nullable = false)
    private String subtaskDescription;

    @Column(nullable = false)
    private boolean isDone;

    @ManyToOne
    private WeddingParentTask weddingParentTask;

    public WeddingSubtask() {
        
    }
    
    public Long getWeddingSubtaskId() {
        return weddingSubtaskId;
    }

    public void setWeddingSubtaskId(Long weddingSubtaskId) {
        this.weddingSubtaskId = weddingSubtaskId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (weddingSubtaskId != null ? weddingSubtaskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the weddingSubtaskId fields are not set
        if (!(object instanceof WeddingSubtask)) {
            return false;
        }
        WeddingSubtask other = (WeddingSubtask) object;
        if ((this.weddingSubtaskId == null && other.weddingSubtaskId != null) || (this.weddingSubtaskId != null && !this.weddingSubtaskId.equals(other.weddingSubtaskId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.WeddingTask[ id=" + weddingSubtaskId + " ]";
    }

    /**
     * @return the subtaskDescription
     */
    public String getSubtaskDescription() {
        return subtaskDescription;
    }

    /**
     * @param subtaskDescription the subtaskDescription to set
     */
    public void setSubtaskDescription(String subtaskDescription) {
        this.subtaskDescription = subtaskDescription;
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
     * @return the weddingParentTask
     */
    public WeddingParentTask getWeddingParentTask() {
        return weddingParentTask;
    }

    /**
     * @param weddingParentTask the weddingParentTask to set
     */
    public void setWeddingParentTask(WeddingParentTask weddingParentTask) {
        this.weddingParentTask = weddingParentTask;
    }

}
