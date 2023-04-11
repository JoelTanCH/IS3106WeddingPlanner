/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author wangp
 */
@Entity
public class WeddingTask implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String taskDescription;
    
    @Column(nullable = false)
    private boolean isDone;
    
    @ManyToOne // this is nullable
    private WeddingTask parentTask;

    @ManyToOne(optional = false)
    private WeddingChecklist weddingChecklist;
    
    public WeddingTask() {
    }
    
    
    // orphan removal isn't really needed in this context but just in case.
    // CascadeType.REMOVE is for removing subtasks. When a task is deleted,
    // subtasks should also be deleted.
    @OneToMany(orphanRemoval = true, cascade = { CascadeType.REMOVE, CascadeType.PERSIST }, mappedBy = "parentTask")
    private List<WeddingTask> subtasks;


    
    
    // --- GETTERS & SETTERS ---
    public WeddingChecklist getWeddingChecklist() {
        return weddingChecklist;
    }

    public void setWeddingChecklist(WeddingChecklist weddingChecklist) {
        this.weddingChecklist = weddingChecklist;
    }
    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public boolean isIsDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public WeddingTask getParentTask() {
        return parentTask;
    }

    public void setParentTask(WeddingTask parentTask) {
        this.parentTask = parentTask;
    }
    
    public List<WeddingTask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<WeddingTask> subtasks) {
        this.subtasks = subtasks;
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
        if (!(object instanceof WeddingTask)) {
            return false;
        }
        WeddingTask other = (WeddingTask) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.WeddingTask[ id=" + id + " ]";
    }
    
}
