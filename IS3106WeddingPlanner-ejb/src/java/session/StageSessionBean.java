/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Stage;
import entity.WeddingProject;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.InvalidAssociationException;
import util.exception.InvalidGetException;
import util.exception.InvalidUpdateException;

/**
 *
 * @author leomi
 */
@Stateless
public class StageSessionBean implements StageSessionBeanLocal {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;
    
    public Long createStage(Stage stage, Long weddingId) throws InvalidAssociationException {
        WeddingProject wd = em.find(WeddingProject.class, weddingId);
        if (wd != null) {
            em.persist(stage);
            stage.setWeddingProject(wd);
            em.flush();
            return stage.getStageId() * -1;
        } else {
            throw new InvalidAssociationException();
        }
    }
    
    public void deleteStage(Long stageId) {
        Stage toDelete = em.find(Stage.class, stageId);
        if (toDelete != null) {
            em.remove(toDelete);
        }
    }
    
    public void updateStages(List<Stage> stages, Long weddingId) throws InvalidUpdateException {
        WeddingProject wd = em.find(WeddingProject.class, weddingId);
        if (wd != null) {
            if (!stages.isEmpty()) {
                stages.forEach(s -> s.setStageId(s.getStageId()));
                em.createQuery("DELETE FROM Stage s WHERE s.weddingProject.weddingProjectId = :id").setParameter("id", weddingId).executeUpdate();
                stages.forEach(s -> {
                    em.persist(s);
                    s.setWeddingProject(wd);
                });
            } else {
                em.createQuery("DELETE FROM Stage s WHERE s.weddingProject.weddingProjectId = :id").setParameter("id", weddingId).executeUpdate();
            }
        } else {
            throw new InvalidUpdateException();
        }
    }
    
    public List<Stage> getStages(Long weddingId) throws InvalidGetException {
        WeddingProject wd = em.find(WeddingProject.class, weddingId);
        if (wd != null) {
            List<Stage> result = em.createQuery("SELECT s FROM Stage s WHERE s.weddingProject.weddingProjectId = :weddingId").setParameter("weddingId", weddingId).getResultList();
            result.forEach(s -> em.detach(s));
            result.forEach(s -> {
                s.setWeddingProject(null);
            });
            result.forEach(s -> {
                s.setStageId(s.getStageId() * -1);
            });
            return result;
        } else {
            throw new InvalidGetException();
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
