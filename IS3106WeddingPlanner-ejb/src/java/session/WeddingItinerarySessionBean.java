/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.WeddingItinerary;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Josephine
 */
@Stateless
public class WeddingItinerarySessionBean implements WeddingItinerarySessionBeanLocal {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;
    
    @Override
    public void createNewItinerary(WeddingItinerary i) {
        em.persist(i);
    }
    
    @Override
    public void updateItinerary(WeddingItinerary i) {
        em.merge(i);
    }
    
    @Override
    public void deleteItinerary(Long itineraryId) {
        WeddingItinerary itinerary = em.find(WeddingItinerary.class, itineraryId);
        
        itinerary.setWeddingProject(null);
        em.remove(itinerary);
    }
}
