/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.WeddingOrganiser;
import entity.WeddingProject;
import error.WeddingOrganiserNotFoundException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author PERSONAL
 */
@Stateless
public class WeddingOrganiserSessionBean implements WeddingOrganiserSessionBeanLocal {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;

    @Override
    public void createWeddingOrganiser(WeddingOrganiser w) {
        em.persist(w);
    }

    @Override
    public WeddingOrganiser getWeddingOrganiser(Long wId) throws WeddingOrganiserNotFoundException {
        WeddingOrganiser w = em.find(WeddingOrganiser.class, wId);

        if (w != null) {
            return w;
        } else {
            throw new WeddingOrganiserNotFoundException("Wedding Organiser Not Found");
        }
    }

    @Override
    public void updateWeddingOrganiser(WeddingOrganiser w) throws WeddingOrganiserNotFoundException {
        WeddingOrganiser wOld = getWeddingOrganiser(w.getUserId());
        
        wOld.setUsername(w.getUsername());
        wOld.setEmail(w.getEmail());
        wOld.setIsBanned(w.isIsBanned());
    }
    
    @Override
    public void deleteWeddingOrganiser(Long wId) throws WeddingOrganiserNotFoundException {
        WeddingOrganiser w = getWeddingOrganiser(wId);
        em.remove(w);
    }
     

}
