/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.WeddingChecklist;
import entity.WeddingParentTask;
import entity.WeddingProject;
import entity.WeddingSubtask;
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

            if (checklist.getWeddingParentTasks() != null) {
                checklist.setWeddingParentTasks(null);
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
    public Long createParentTask(WeddingParentTask t, Long checklistId) throws InvalidAssociationException {
        WeddingChecklist checklist = em.find(WeddingChecklist.class, checklistId);
        if (t != null && checklist != null) {
            em.persist(t);
            t.setWeddingChecklist(checklist);
            checklist.getWeddingParentTasks().add(t);
            em.flush();
            return t.getWeddingParentTaskId();
        } else {
            throw new InvalidAssociationException();
        }
    }

    @Override
    public List<WeddingParentTask> getAllTasks() {
        Query query = em.createQuery("SELECT p FROM WeddingParentTask p JOIN FETCH p.weddingSubtasks");
        List<WeddingParentTask> parentTasks = query.getResultList();

        for (WeddingParentTask parentTask : parentTasks) {
            if (parentTask.getWeddingChecklist() != null) {
                em.detach(parentTask.getWeddingChecklist());
                parentTask.setWeddingChecklist(null);
            }
        }
        return parentTasks;
    }

    @Override
    public void updateParentTask(WeddingParentTask parentTask) {
        WeddingParentTask newParentTask = em.find(WeddingParentTask.class, parentTask.getWeddingParentTaskId());
        if (newParentTask != null) {
            newParentTask.setTaskDescription(parentTask.getTaskDescription());
            newParentTask.setIsDone(parentTask.isIsDone());
        }
    }

    @Override
    public void deleteParentTask(Long parentTaskId) {
        WeddingParentTask parentTask = em.find(WeddingParentTask.class, parentTaskId);
        if (parentTask != null) {
            WeddingChecklist weddingChecklist = parentTask.getWeddingChecklist();
            weddingChecklist.getWeddingParentTasks().remove(parentTask);
            em.remove(parentTask);
        }
    }

    @Override
    public Long createSubtask(WeddingSubtask t, Long parentTaskId) throws InvalidAssociationException {
        WeddingParentTask parentTask = em.find(WeddingParentTask.class, parentTaskId);
        if (t != null && parentTask != null) {
            em.persist(t);
            t.setWeddingParentTask(parentTask);
            parentTask.getWeddingSubtasks().add(t);
            em.flush();
            return t.getWeddingSubtaskId();
        } else {
            throw new InvalidAssociationException();
        }
    }

    @Override
    public List<WeddingParentTask> getAllWeddingParentTasks() {
        Query query = em.createQuery("SELECT p FROM WeddingParentTask p");
        List<WeddingParentTask> tasks = query.getResultList();

        for (WeddingParentTask task : tasks) {
            if (task.getWeddingChecklist() != null) {
                em.detach(task);
                task.setWeddingChecklist(null); 
                
                if (task.getWeddingSubtasks() != null) {
                    for (WeddingSubtask subtask : task.getWeddingSubtasks()) {
                        em.detach(subtask);
                        subtask.setWeddingParentTask(null);
                    }
                }
            }
        }
        return tasks;
    }

    @Override
    public List<WeddingSubtask> getAllWeddingSubtasks() {
        Query query = em.createQuery("SELECT s FROM WeddingSubtask s");
        List<WeddingSubtask> subtasks = query.getResultList();

        for (WeddingSubtask subtask : subtasks) {
            if (subtask.getWeddingParentTask() != null) {
                em.detach(subtask);
                subtask.setWeddingParentTask(null);
            }
        }
        return subtasks;
    }

    @Override
    public List<WeddingSubtask> getAllWeddingSubtasks(Long weddingParentTaskId) {
        WeddingParentTask parentTask = em.find(WeddingParentTask.class, weddingParentTaskId);
        List<WeddingSubtask> subtasks = new ArrayList<WeddingSubtask>();

        if (parentTask != null) {
            subtasks = parentTask.getWeddingSubtasks();

            for (WeddingSubtask subtask : subtasks) {
                if (subtask.getWeddingParentTask() != null) {
                    em.detach(subtask);
                    subtask.setWeddingParentTask(null);
                }
            }
        }
        return subtasks;
    }

    @Override
    public void updateSubtask(WeddingSubtask subtask) {
        WeddingSubtask newSubtask = em.find(WeddingSubtask.class, subtask.getWeddingSubtaskId());
        if (newSubtask != null) {
            newSubtask.setSubtaskDescription(subtask.getSubtaskDescription());
            newSubtask.setIsDone(subtask.isIsDone());
        }
    }

    @Override
    public void deleteSubtask(Long subtaskId) {
        WeddingSubtask subtask = em.find(WeddingSubtask.class, subtaskId);
        if (subtask != null) {
            WeddingParentTask weddingParentTask = subtask.getWeddingParentTask();
            weddingParentTask.getWeddingSubtasks().remove(subtask);
            em.remove(subtask);
        }
    }

//
//    @Override
//    public Long createTask(WeddingTask t, Long checklistId) throws InvalidAssociationException {
//        // add t to WeddingProject's WeddingChecklist's List<WeddingTask> attribute
//        WeddingChecklist checklist = em.find(WeddingChecklist.class, checklistId);
//        if (t != null && checklist != null) {
//            em.persist(t);
//            t.setWeddingChecklist(checklist);
//            checklist.getWeddingTasks().add(t);
//            em.flush();
//            return t.getId();
//        } else {
//            throw new InvalidAssociationException();
//        }
//    }
//
//    @Override
//    public List<WeddingTask> getAllParentTasks() {
//        Query query = em.createQuery("SELECT task FROM WeddingTask task WHERE task.parentTask IS NULL");
//        List<WeddingTask> tasks = query.getResultList();
//
//        for (WeddingTask task : tasks) {
//            em.detach(task);
//            task.setWeddingChecklist(null);
//        }
//        return tasks;
//    }
//
////    @Override
////    public List<WeddingTask> getAllSubTasks() {
////        WeddingTask parentTask = em.find(WeddingTask.class, parentTaskId);
////        List<WeddingTask> subtasks = parentTask.getSubtasks();
////
//////        for (WeddingTask task : subtasks) {
//////            em.detach(task);
//////            task.setWeddingChecklist(null);
//////        }
////        return subtasks;
////    }
//    @Override
//    public void updateTask(WeddingTask t) {
//        WeddingTask newTask = em.find(WeddingTask.class, t.getId());
//        if (newTask != null) {
//            newTask.setTaskDescription(t.getTaskDescription());
//            newTask.setIsDone(t.isIsDone());
//        }
//    }
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
