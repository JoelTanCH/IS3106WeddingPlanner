/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Guest;
import entity.GuestTable;
import entity.WeddingProject;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author leomi
 */


@Stateless
public class GuestSessionBean implements GuestSessionBeanLocal {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;

    @Override
    public void createGuest(Guest guest, Long weddingProjectId) {
        WeddingProject weddingProject = em.find(WeddingProject.class, weddingProjectId);
        em.persist(guest);
        guest.setWeddingProject(weddingProject);
        weddingProject.getGuests().add(guest);
    }
   
    
    @Override
    public void deleteGuest(Long guestId) {
        Guest guest = em.find(Guest.class, guestId);
        WeddingProject weddingProject = guest.getWeddingProject();
        weddingProject.getGuests().remove(guest);
        if (guest.getGuestTable() != null) {
            GuestTable table = guest.getGuestTable();
            table.getGuests().remove(guest);
            guest.setGuestTable(null);
        }
        em.remove(guest);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
