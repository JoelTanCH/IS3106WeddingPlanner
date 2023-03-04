/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.WeddingTask;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wangp
 */
@Stateless
public class WeddingChecklistBean implements WeddingChecklistBeanLocal {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;

    // --- CRUD for WeddingTask ---
    // need to wait and see WeddingChecklist object and the List<WeddingTask> it has
    
    public void createTask(WeddingTask t) {
        // add t to WeddingProject's WeddingChecklist's List<WeddingTask> attribute
        em.persist(t);
    }
    
    // for editing of description?
    public void updateTask(WeddingTask t) {
        em.merge(t);
    }
    
    // WIP, this will depend on structure of the main list
    public void addSubtaskToTask(WeddingTask parentTask, WeddingTask t) {
        t.setParentTask(parentTask);
        parentTask.getSubtasks().add(t);
    }

    
    
    public void removeTask(WeddingTask t) {
        WeddingTask parentTask = t.getParentTask();
        if (parentTask != null) {
            parentTask.getSubtasks().remove(t);
        }
        
        em.remove(t); // don't have to worry about t's subtasks because CascadeType.REMOVE
    }

    // --- END CRUD for WeddingTask ---
    
    // --- CHECKING OFF TASK Methods ---
    
    
    public void checkOffTask(WeddingTask t) {
        t.setIsDone(!t.isIsDone());

        for (WeddingTask subtask : t.getSubtasks() ) {
            checkOffTask(subtask, t.isIsDone());
        }

    }

    // this is for the nested subtasks
    // if the no-parameter version is used, the isDone inside would reverse,
    // which is the wrong behaviour, the boolean should be set to the top-level parent task boolean value
    public void checkOffTask(WeddingTask t, boolean valueToSet) {
        t.setIsDone(valueToSet);

        for (WeddingTask subtask : t.getSubtasks()) {
            checkOffTask(subtask, valueToSet);
        }
    }

    // maybe we need a 3rd method to check whether all subtasks are checked,
    // and if so check off the parent task
    
    // --- END CHECKING OFF TASK Methods ---

}
