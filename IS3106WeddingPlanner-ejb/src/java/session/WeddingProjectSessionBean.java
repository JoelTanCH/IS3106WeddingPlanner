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
        w.setWeddingOrganiser(organiser);

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
        wOld.setCompleted(w.isCompleted());
        wOld.setVenue(w.getVenue());
        wOld.setWeddingDate(w.getWeddingDate());
        wOld.setWeddingStartTime(w.getWeddingStartTime());
        wOld.setWeddingEndTime(w.getWeddingEndTime());

    }

    //should everything such as request, tables, etc be deleted if the wedding project is deleted ?
    @Override
    public void deleteWeddingProject(Long wId) throws WeddingProjectNotFoundException, InvalidDeleteException {
        WeddingProject w = getWeddingProject(wId);

        List<Guest> guest = w.getGuests();
        w.setGuests(null);
        for (Guest s : guest) {
            s.setWeddingProject(null);
        }

        List<Request> requests = w.getRequests();
        w.setRequests(null);
        for (Request r : requests) {
            r.setWeddingProject(null);
        }

        List<GuestTable> table = w.getTables();
        w.setTables(null);
        for (GuestTable t : table) {
            t.setWeddingProject(null);
        }

        w.getWeddingBudgetList().setWeddingProject(null);
        w.setWeddingBudgetList(null);

        w.getWeddingChecklist().setWeddingProject(null);
        w.setWeddingChecklist(null);

        List<WeddingItinerary> weddingItineraries = w.getWeddingItineraries();
        w.setWeddingItineraries(null);
        for (WeddingItinerary itinerary : weddingItineraries) {
            itinerary.setWeddingProject(null);
        }

        w.getWeddingOrganiser().getWeddingProjects().remove(w);
        w.setWeddingOrganiser(null);

        em.remove(w);
    }

    @Override
    public List<WeddingProject> getAllWeddingProjectbyOrganiser(Long wId) throws WeddingOrganiserNotFoundException {
        WeddingOrganiser w = em.find(WeddingOrganiser.class, wId);
        if (w != null) {
            List<WeddingProject> project = w.getWeddingProjects();
            return project;
        } else {
            throw new WeddingOrganiserNotFoundException("Wedding Organiser Not Found");
        }

    }

    @Override
    public List<WeddingProject> getAllCompletedWeddingProject(Long wId) throws WeddingOrganiserNotFoundException {
        WeddingOrganiser w = em.find(WeddingOrganiser.class, wId);
        // although em.find(WeddingOrg) is technically not needed, it triggers the exception throwing in case of failure so i left it in
        if (w != null) {
            Query q = em.createQuery("SELECT p FROM WeddingOrganiser w JOIN w.weddingProjects p WHERE w.userId = :wId AND p.completed = true");
            q.setParameter("wId", wId);
            return q.getResultList();

            // Can also get the same result programmatically with a for loop
            // since we have WeddingOrganiser w, we can do a for loop through w.weddingProjects and check each weddingProject.completed boolean
            // then push those that match our condition into a list to return
            // but using JPQL to fetch it straight from the db is less lengthy though the efficiency is questionable?
        } else {
            throw new WeddingOrganiserNotFoundException("Wedding Organiser Not Found");
        }

    }

    @Override
    public List<WeddingProject> getAllNotCompletedWeddingProject(Long wId) throws WeddingOrganiserNotFoundException {
        WeddingOrganiser w = em.find(WeddingOrganiser.class, wId);
        // although em.find(WeddingOrg) is technically not needed, it triggers the exception throwing in case of failure so i left it in

        if (w != null) {
            Query q = em.createQuery("SELECT p FROM WeddingOrganiser w JOIN w.weddingProjects p WHERE w.userId = :wId AND p.completed = false");
            q.setParameter("wId", wId);
            return q.getResultList();
        } else {
            throw new WeddingOrganiserNotFoundException("Wedding Organiser Not Found");
        }
    }

    @Override
    public List<WeddingProject> getAllWeddingProject() {
        Query q = em.createQuery("SELECT w FROM WeddingProject w");
        return q.getResultList();
    }

    @Override
    public List<WeddingProject> searchWeddingProjectbyName(String name) throws WeddingProjectNotFoundException {
        try {
            Query q = em.createQuery("SELECT w FROM WeddingProject w WHERE w.name LIKE :name");
            q.setParameter("name", "%" + name + "%");
            return q.getResultList();
        } catch (Exception e) {
            throw new WeddingProjectNotFoundException(e.getMessage());
        }
    }

}
