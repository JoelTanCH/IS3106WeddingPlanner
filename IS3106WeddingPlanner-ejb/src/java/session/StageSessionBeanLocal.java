/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Stage;
import java.util.List;
import javax.ejb.Local;
import util.exception.InvalidAssociationException;
import util.exception.InvalidGetException;
import util.exception.InvalidUpdateException;

/**
 *
 * @author leomi
 */
@Local
public interface StageSessionBeanLocal {

    public List<Stage> getStages(Long weddingId) throws InvalidGetException;

    public void updateStages(List<Stage> stages, Long weddingId) throws InvalidUpdateException;

    public void deleteStage(Long stageId);

    public Long createStage(Stage stage, Long weddingId) throws InvalidAssociationException;
    
}
