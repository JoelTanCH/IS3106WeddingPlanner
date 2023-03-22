/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Guest;
import entity.GuestTable;
import entity.WeddingProject;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 *
 * @author leomi
 */
import util.exception.InvalidAssociationException;
@Stateless
public class GuestTableSessionBean implements GuestTableSessionBeanLocal {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;

    @Override
    public void createGuestTable(GuestTable guestTable, Long weddingProjectId) {
        WeddingProject weddingProject = em.find(WeddingProject.class, weddingProjectId);
        em.persist(guestTable);
        guestTable.setWeddingProject(weddingProject);
        weddingProject.getTables().add(guestTable);
    }
    
    @Override
    public void addGuestToTable(Long guestId, Long guestTableId) throws InvalidAssociationException {
        Guest guest = em.find(Guest.class, guestId);
        GuestTable guestTable = em.find(GuestTable.class, guestTableId);
        if (!guest.getWeddingProject().equals(guestTable.getWeddingProject())) {
            throw new InvalidAssociationException();
        }
        guest.setGuestTable(guestTable);
        guestTable.getGuests().add(guest);
    }
    
    @Override
    public void removeGuestFromTable(Long guestId, Long guestTableId) throws InvalidAssociationException {
        Guest guest = em.find(Guest.class, guestId);
        GuestTable guestTable = em.find(GuestTable.class, guestTableId);
        if (!guest.getWeddingProject().equals(guestTable.getWeddingProject())) {
            throw new InvalidAssociationException();
        }
        guest.setGuestTable(null);
        guestTable.getGuests().remove(guest);
    }
    
    @Override
    public void deleteGuestTable(Long guestTableId) {
        GuestTable guestTable = em.find(GuestTable.class, guestTableId);
        WeddingProject weddingProject = guestTable.getWeddingProject();
        if (!guestTable.getGuests().isEmpty()) {
            List<Guest> guests = guestTable.getGuests();
            guests.forEach(guest -> guest.setGuestTable(null));
            guestTable.getGuests().clear();
        }
        weddingProject.getTables().remove(guestTable);
        em.remove(guestTable);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
