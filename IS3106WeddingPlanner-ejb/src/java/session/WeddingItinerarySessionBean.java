/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.WeddingItinerary;
import entity.WeddingProject;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidAssociationException;

/**
 *
 * @author Josephine
 */
@Stateless
public class WeddingItinerarySessionBean implements WeddingItinerarySessionBeanLocal {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;
    
    @Override
    public Long createNewItinerary(WeddingItinerary i, Long weddingProjectId) throws InvalidAssociationException {
        WeddingProject weddingProject = em.find(WeddingProject.class, weddingProjectId);
        if (i != null && weddingProject != null) {
            em.persist(i);
            
            i.setWeddingProject(weddingProject);
            weddingProject.getWeddingItineraries().add(i);
            em.flush();
            return i.getWeddingItineraryId();
        } else {
            throw new InvalidAssociationException();
        }
    }
    
    @Override
    public List<WeddingItinerary> getAllItineraries() {
        Query query = em.createQuery("SELECT i FROM WeddingItinerary i");
        List<WeddingItinerary> itineraries = query.getResultList();
        
        for (WeddingItinerary itinerary : itineraries) {
            if (itinerary.getWeddingProject() != null) {
                em.detach(itinerary);
                itinerary.setWeddingProject(null);
            }
        }
        return itineraries;
    }
    
    @Override
    public WeddingItinerary getItinerary(Long weddingItineraryId) {
        return em.find(WeddingItinerary.class, weddingItineraryId);
    }
//    @Override
//    public void createNewItinerary(WeddingItinerary i) {
//        em.persist(i);
//    }
    
    @Override
    public void updateItinerary(WeddingItinerary i) {
        WeddingItinerary weddingItinerary = em.find(WeddingItinerary.class, i.getWeddingItineraryId());
        if (weddingItinerary != null) {
            weddingItinerary.setEventName(i.getEventName());
            weddingItinerary.setEventDate(i.getEventDate());
            weddingItinerary.setEventStartTime(i.getEventStartTime());
            weddingItinerary.setEventEndTime(i.getEventEndTime());
        }
    }
    
    @Override
    public void deleteItinerary(Long itineraryId) {
        WeddingItinerary itinerary = em.find(WeddingItinerary.class, itineraryId);
        
        itinerary.setWeddingProject(null);
        em.remove(itinerary);
    }
    
    //code for Guest View (part of guest management)
    @Override
    public List<WeddingItinerary> getWeddingItinerary(Long weddingId) {
        if (weddingId != null) {
           List<WeddingItinerary> itinerary = em.createQuery("SELECT w FROM WeddingItinerary w WHERE w.weddingProject.weddingProjectId = :wId").setParameter("wId", weddingId).getResultList();
           itinerary.forEach(i -> {
               em.detach(i);
               i.setWeddingProject(null);
           });
           itinerary.sort((x,y) -> {
               if (x.getEventDate().after(y.getEventDate())) {
                   return 1;
               } else if (x.getEventDate().before(y.getEventDate())) {
                   return -1;
               } else if (x.getEventStartTime().after(y.getEventStartTime())) {
                   return 1;
               } else if (x.getEventStartTime().before(y.getEventStartTime())) {
                   return -1;
               } else {
                   return 0;
               }
           });
           return itinerary;
        }
        return new ArrayList<>();
    } 
    // idk what this is but without this there is an error
    @Override
    public List<WeddingItinerary> getWeddingItinerariesByWeddingProject(Long weddingId) {
        WeddingProject weddingProject = em.find(WeddingProject.class, weddingId);
        Query query = em.createQuery("SELECT i FROM WeddingItinerary i");
        List<WeddingItinerary> itineraries = query.getResultList();
        
        List<WeddingItinerary> newItineraries = new ArrayList<>();
        if (weddingProject != null) {
            for (WeddingItinerary itinerary : itineraries) {
                if (itinerary.getWeddingProject().equals(weddingProject)) {
                    newItineraries.add(itinerary);
                }
            }
        }
        
        for (WeddingItinerary itinerary : itineraries) {
            if (itinerary.getWeddingProject() != null) {
                em.detach(itinerary);
                itinerary.setWeddingProject(null);
            }
        }
        return newItineraries;
    }
}
