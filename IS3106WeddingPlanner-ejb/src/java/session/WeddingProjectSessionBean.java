/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Guest;
import entity.GuestTable;
import entity.Request;
import entity.WeddingItinerary;
import entity.WeddingOrganiser;
import entity.WeddingProject;
import error.WeddingOrganiserNotFoundException;
import error.WeddingProjectNotFoundException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidDeleteException;

/**
 *
 * @author PERSONAL
 */
@Stateless
public class WeddingProjectSessionBean implements WeddingProjectSessionBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;

    /*@EJB
    WeddingOrganiserSessionBean weddingOrganiserSessionBean;

    @EJB
    GuestSessionBean guestSessionBean;

    @EJB
    GuestTableSessionBean guestTableSessionBean;

    @EJB
    RequestSessionBean requestSessionBean;

    @EJB
    WeddingBudgetSessionBean weddingBudgetSessionBean;

    @EJB
    WeddingChecklistBean weddingChecklistSessionBean;

    @EJB
    WeddingItinerarySessionBean weddingItinerarySessionBean;*/

    @Override
    public void createWeddingProject(Long organiserId, WeddingProject w) {
        WeddingOrganiser organiser = em.find(WeddingOrganiser.class, organiserId);
        organiser.getWeddingProjects().add(w);

        em.persist(w);
    }

    @Override
    public WeddingProject getWeddingProject(Long wId) throws WeddingProjectNotFoundException {
        WeddingProject w = em.find(WeddingProject.class, wId);
        if (w != null) {
            return w;
        } else {
            throw new WeddingProjectNotFoundException("Wedding Project Not Found");
        }
    }

    @Override
    public void updateWeddingProject(WeddingProject w) throws WeddingProjectNotFoundException {
        WeddingProject wOld = getWeddingProject(w.getWeddingProjectId());
        wOld.setName(w.getName());
        wOld.setDescription(w.getDescription());
    }

    //should everything such as request, tables, etc be deleted if the wedding project is deleted ?
    @Override
    public void deleteWeddingProject(Long wId) throws WeddingProjectNotFoundException, InvalidDeleteException {
        WeddingProject w = getWeddingProject(wId);

        List<Guest> guest = w.getGuests();
        w.setGuests(null);
        for (Guest s : guest) {
            //guestSessionBean.deleteGuest(s.getId());
            s.setWeddingProject(null);
        }

        List<Request> requests = w.getRequests();
        w.setRequests(null);
        for (Request r : requests) {
            //requestSessionBean.deleteRequest(r.getRequestId());
            r.setWeddingProject(null);
        }

        List<GuestTable> table = w.getTables();
        w.setTables(null);
        for (GuestTable t : table) {
            //guestTableSessionBean.deleteGuestTable(t.getId());
            t.setWeddingProject(null);
        }

        //weddingBudgetSessionBean.deleteWeddingBudgetList(w.getWeddingBudgetList().getWeddingBudgetListId());
        
        w.getWeddingBudgetList().setWeddingProject(null);
        w.setWeddingBudgetList(null);
        

        //weddingChecklistSessionBean.deleteWeddingChecklist(w.getWeddingChecklist().getWeddingCheckListId());
        w.getWeddingChecklist().setWeddingProject(null);
        w.setWeddingChecklist(null);
        

        List<WeddingItinerary> weddingItineraries = w.getWeddingItineraries();
        w.setWeddingItineraries(null);
        for (WeddingItinerary itinerary : weddingItineraries) {
            //weddingItinerarySessionBean.deleteItinerary(itinerary.getWeddingItineraryId());
            itinerary.setWeddingProject(null);
        }

        w.getWeddingOrganiser().getWeddingProjects().remove(w);
        w.setWeddingOrganiser(null);
        

        em.remove(w);
    }

    @Override
    public List<WeddingProject> getAllWeddingProjectbyOrganiser(Long wId) throws WeddingOrganiserNotFoundException {
        WeddingOrganiser w = em.find(WeddingOrganiser.class, wId);
        List<WeddingProject> project = w.getWeddingProjects();
        return project;
    }
    
     @Override
    public List<WeddingProject> getAllCompletedWeddingProject(Long wId) throws WeddingOrganiserNotFoundException {
        WeddingOrganiser w = em.find(WeddingOrganiser.class, wId);
        Query q = em.createQuery("SELECT w FROM WeddingOrganiser w WHERE w.weddingProjects.completed = true");
        q.setParameter("w", w);
        return q.getResultList();
    }
    
     @Override
    public List<WeddingProject> getAllNotCompletedWeddingProject(Long wId) throws WeddingOrganiserNotFoundException {
        WeddingOrganiser w = em.find(WeddingOrganiser.class, wId);
        Query q = em.createQuery("SELECT w FROM WeddingOrganiser w WHERE w.weddingProjects.completed = false");
        q.setParameter("w", w);
        return q.getResultList();
    }

    @Override
    public List<WeddingProject> getAllWeddingProject() {
        Query q = em.createQuery("SELECT w FROM WeddingProject w");
        return q.getResultList();
    }

    @Override
    public List<WeddingProject> searchWeddingProjectbyName(String name) throws WeddingProjectNotFoundException {
        Query q = em.createQuery("SELECT w FROM WeddingProject w WHERE w.name LIKE :name");
        q.setParameter("name", "%" + name + "%");
        return q.getResultList();
    }

}
