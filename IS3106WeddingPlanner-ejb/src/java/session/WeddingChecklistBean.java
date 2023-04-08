/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.WeddingChecklist;
import entity.WeddingProject;
import entity.WeddingTask;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidAssociationException;

/**
 *
 * @author wangp
 */
@Stateless
public class WeddingChecklistBean implements WeddingChecklistBeanLocal {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;

    @Override
    public Long createWeddingChecklist(WeddingChecklist weddingChecklist, Long weddingProjectId) throws InvalidAssociationException {
        WeddingProject weddingProject = em.find(WeddingProject.class, weddingProjectId);
        if (weddingChecklist != null && weddingProject != null) {
            em.persist(weddingChecklist);
            weddingChecklist.setWeddingProject(weddingProject);
            em.flush();
            return weddingChecklist.getWeddingCheckListId();
        } else {
            throw new InvalidAssociationException();
        }
    }

    @Override
    public List<WeddingChecklist> getAllWeddingChecklists() {
        Query query = em.createQuery("SELECT c FROM WeddingChecklist c");
        List<WeddingChecklist> checklists = query.getResultList();

        for (WeddingChecklist checklist : checklists) {
            if (checklist.getWeddingProject() != null) {
                em.detach(checklist);
                checklist.setWeddingProject(null);
            }
            
            if (checklist.getWeddingTasks() != null) {
                checklist.setWeddingTasks(null);
            }
        }
        return checklists;
    }

    @Override
    public WeddingChecklist getWeddingChecklist(Long checklistId) {
        WeddingChecklist checklist = em.find(WeddingChecklist.class, checklistId);
        return checklist;
    }

    @Override
    public Long createTask(WeddingTask t, Long checklistId) throws InvalidAssociationException {
        // add t to WeddingProject's WeddingChecklist's List<WeddingTask> attribute
        WeddingChecklist checklist = em.find(WeddingChecklist.class, checklistId);
        if (t != null && checklist != null) {
            em.persist(t);
            t.setWeddingChecklist(checklist);
            checklist.getWeddingTasks().add(t);
            em.flush();
            return t.getId();
        } else {
            throw new InvalidAssociationException();
        }
    }

//    @Override
//    public List<WeddingTask> getAllParentTasks() {
//        Query query = em.createQuery("SELECT task FROM WeddingTask task");
//        List<WeddingTask> tasks = query.getResultList();
//
//        List<WeddingTask> parentTasks = new ArrayList<WeddingTask>();
//        for (WeddingTask task : tasks) {
//            if (task.getWeddingChecklist() == null) {
//                em.detach(task);
//                parentTasks.add(task);
//            }
//        }
//        return parentTasks;
//    }
//
//    @Override
//    public List<WeddingTask> getAllSubTasks() {
//        WeddingTask parentTask = em.find(WeddingTask.class, parentTaskId);
//        List<WeddingTask> subtasks = parentTask.getSubtasks();
//
////        for (WeddingTask task : subtasks) {
////            em.detach(task);
////            task.setWeddingChecklist(null);
////        }
//        return subtasks;
//    }

    @Override
    public void updateTask(WeddingTask t) {
        WeddingTask newTask = em.find(WeddingTask.class, t.getId());
        if (newTask != null) {
            newTask.setTaskDescription(t.getTaskDescription());
            newTask.setIsDone(t.isIsDone());
        }
    }

    // WIP, this will depend on structure of the main list
//    public void addSubtaskToTask(WeddingTask parentTask, WeddingTask t) {
//        t.setParentTask(parentTask);
//        parentTask.getSubtasks().add(t);
//    }
//
//    public void removeTask(WeddingTask t) {
//        WeddingTask parentTask = t.getParentTask();
//        WeddingChecklist checklist = t.getWeddingChecklist();
//        checklist.getWeddingTasks().remove(t); // remove it from wedding checklist
//        if (parentTask != null) {
//            parentTask.getSubtasks().remove(t);
//        }
//
//        em.remove(t); // don't have to worry about t's subtasks because CascadeType.REMOVE
//    }
//
//    // --- END CRUD for WeddingTask ---
//    // --- CHECKING OFF TASK Methods ---
//    public void checkOffTask(WeddingTask t) {
//        t.setIsDone(!t.isIsDone());
//
//        for (WeddingTask subtask : t.getSubtasks()) {
//            checkOffTask(subtask, t.isIsDone());
//        }
//
//    }
//
//    // this is for the nested subtasks
//    // if the no-parameter version is used, the isDone inside would reverse,
//    // which is the wrong behaviour, the boolean should be set to the top-level parent task boolean value
//    public void checkOffTask(WeddingTask t, boolean valueToSet) {
//        t.setIsDone(valueToSet);
//
//        for (WeddingTask subtask : t.getSubtasks()) {
//            checkOffTask(subtask, valueToSet);
//        }
//    }
//
//    // maybe we need a 3rd method to check whether all subtasks are checked,
//    // and if so check off the parent task
//    // --- END CHECKING OFF TASK Methods ---
}
