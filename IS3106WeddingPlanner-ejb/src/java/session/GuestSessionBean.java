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
import javax.persistence.Query;
import util.exception.InvalidAssociationException;
import util.exception.InvalidDeleteException;
import util.exception.InvalidGetException;
import util.exception.InvalidUpdateException;

/**
 *
 * @author leomi
 */


@Stateless
public class GuestSessionBean implements GuestSessionBeanLocal {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;

    @Override
    public Long createGuest(Guest guest, Long weddingProjectId) throws InvalidAssociationException {
        WeddingProject weddingProject = em.find(WeddingProject.class, weddingProjectId);
        if (weddingProject != null && guest != null) {
            em.persist(guest);
      
            guest.setWeddingProject(weddingProject);
            weddingProject.getGuests().add(guest);
            em.flush();
            return guest.getId();
        } else {
            throw new InvalidAssociationException();
        }
    }
   
    @Override
    public void updateGuest(Guest guest) throws InvalidUpdateException {
        if (guest != null) {
            Guest toUpdate = em.find(Guest.class, guest.getId());
            if (toUpdate != null && guest.getGuestTable() != null) {
                GuestTable table = em.find(GuestTable.class, guest.getGuestTable().getId());
                if (table == null) {
                    throw new InvalidUpdateException();
                }
                toUpdate.setGuestTable(table);
            } else if (toUpdate == null) {
                throw new InvalidUpdateException();
            }
            toUpdate.setAttendingSide(guest.getAttendingSide());
            toUpdate.setEmail(guest.getEmail());
            toUpdate.setName(guest.getName());
            toUpdate.setNumPax(guest.getNumPax());
            toUpdate.setRsvp(guest.getRsvp());
        } else {
            throw new InvalidUpdateException();
        }

    }
    @Override
    public void deleteGuest(Long guestId) throws InvalidDeleteException {
        Guest guest = em.find(Guest.class, guestId);
        if (guest != null) {
            WeddingProject weddingProject = guest.getWeddingProject();
            weddingProject.getGuests().remove(guest);
            if (guest.getGuestTable() != null) {
                GuestTable table = guest.getGuestTable();
                table.getGuests().remove(guest);
                guest.setGuestTable(null);
            }
            em.remove(guest);
        } else {
            throw new InvalidDeleteException();
        }
    }
    
    @Override
    public List<Guest> getGuests(Long weddingId) throws InvalidGetException {
        System.out.println("HERE");
        if (weddingId != null && em.find(WeddingProject.class, weddingId) != null) {
            Query query = em.createQuery("SELECT g FROM Guest g WHERE g.weddingProject.weddingProjectId = ?1").setParameter(1, weddingId);
            List<Guest> guests = query.getResultList();
            guests.forEach(g -> em.detach(g));
            guests.forEach(guest -> {
                if (guest.getGuestTable() != null) {
                    em.detach(guest.getGuestTable());
                }
                    });
            return guests;
        } else {
            throw new InvalidGetException();
        }
    }
 

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
