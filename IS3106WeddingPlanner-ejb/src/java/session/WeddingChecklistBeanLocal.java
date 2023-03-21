/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.WeddingChecklist;
import entity.WeddingTask;
import javax.ejb.Local;

/**
 *
 * @author wangp
 */
@Local
public interface WeddingChecklistBeanLocal {

    public WeddingChecklist getWeddingChecklist(Long checklistId);

    public void createTask(WeddingTask t, Long checklistId);

    public void updateTask(WeddingTask t);

    public void addSubtaskToTask(WeddingTask parentTask, WeddingTask t);

    public void removeTask(WeddingTask t);

    public void checkOffTask(WeddingTask t);

    public void checkOffTask(WeddingTask t, boolean valueToSet);
    
}
