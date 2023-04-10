/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.WeddingChecklist;
import entity.WeddingParentTask;
import entity.WeddingSubtask;
//import entity.WeddingTask;
import java.util.List;
import javax.ejb.Local;
import util.exception.InvalidAssociationException;

/**
 *
 * @author wangp
 */
@Local
public interface WeddingChecklistBeanLocal {

    public Long createWeddingChecklist(WeddingChecklist weddingChecklist, Long weddingProjectId) throws InvalidAssociationException;

    public List<WeddingChecklist> getAllWeddingChecklists();

    public WeddingChecklist getWeddingChecklist(Long checklistId);

    public Long createParentTask(WeddingParentTask t, Long checklistId) throws InvalidAssociationException;

    public List<WeddingParentTask> getAllWeddingParentTasks();

    public void updateParentTask(WeddingParentTask parentTask);

    public void deleteParentTask(Long parentTaskId);

    public Long createSubtask(WeddingSubtask t, Long parentTaskId) throws InvalidAssociationException;

    public List<WeddingSubtask> getAllWeddingSubtasks();

    public void updateSubtask(WeddingSubtask subtask);

    public void deleteSubtask(Long subtaskId);
    
    //    public WeddingChecklist getWeddingChecklist(Long checklistId);
//
//    public Long createTask(WeddingTask t, Long checklistId) throws InvalidAssociationException;

//    public void updateTask(WeddingTask t);
//
//    public void addSubtaskToTask(WeddingTask parentTask, WeddingTask t);
//
//    public void removeTask(WeddingTask t);
//
//    public void checkOffTask(WeddingTask t);
//
//    public void checkOffTask(WeddingTask t, boolean valueToSet);
//
//    public Long createWeddingChecklist(WeddingChecklist weddingChecklist, Long weddingProjectId);

//    public Long createWeddingChecklist(WeddingChecklist weddingChecklist, Long weddingProjectId) throws InvalidAssociationException;
//
//    public List<WeddingChecklist> getAllWeddingChecklists();
//
//    public WeddingChecklist getWeddingChecklist(Long checklistId);
//
//    public Long createTask(WeddingTask t, Long checklistId) throws InvalidAssociationException;
//
//    public List<WeddingTask> getAllParentTasks();
//
//    public List<WeddingTask> getAllSubTasks();
//
//    public void updateTask(WeddingTask t);
    
}
