/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.WeddingChecklist;
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
    // Probably don't need? Because WeddingChecklist should be automaticalled instantialised
    // upon the creation of a WeddingProject. Furthermore, it cannot be removed/deleted.
    // (WeddingProject should only be able to be deleted if no vendors hired?)
    //
    // however just in case some CRUD code is here anyway
    
    public WeddingChecklist getWeddingChecklist(Long checklistId) {
        WeddingChecklist checklist = em.find(WeddingChecklist.class, checklistId);
        return checklist;
    }
    
    
    
    // 
    public void createTask(WeddingTask t, Long checklistId) {
        // add t to WeddingProject's WeddingChecklist's List<WeddingTask> attribute
        WeddingChecklist checklist = em.find(WeddingChecklist.class, checklistId);
        checklist.getWeddingTasks().add(t);

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
        WeddingChecklist checklist = t.getWeddingChecklist();
        checklist.getWeddingTasks().remove(t); // remove it from wedding checklist
        if (parentTask != null) {
            parentTask.getSubtasks().remove(t);
        }

        em.remove(t); // don't have to worry about t's subtasks because CascadeType.REMOVE
    }

    // --- END CRUD for WeddingTask ---
    // --- CHECKING OFF TASK Methods ---
    public void checkOffTask(WeddingTask t) {
        t.setIsDone(!t.isIsDone());

        for (WeddingTask subtask : t.getSubtasks()) {
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
