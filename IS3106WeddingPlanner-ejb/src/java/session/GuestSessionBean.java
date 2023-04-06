/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Guest;
import entity.GuestTable;
import entity.WeddingProject;
import enumeration.StatusEnum;
import java.util.List;
import java.util.Optional;
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
                if (toUpdate.getGuestTable() == null) { //guest has no previous table
                    GuestTable table = em.find(GuestTable.class, guest.getGuestTable().getId());
                    if (table == null) {
                        throw new InvalidUpdateException();
                    }
                    table.setCurrOccupancy(table.getCurrOccupancy() + guest.getNumPax());
                    toUpdate.setGuestTable(table);
                } else {
                     GuestTable table = em.find(GuestTable.class, guest.getGuestTable().getId());
                     if (table == null) {
                        throw new InvalidUpdateException();
                     }
                    table.setCurrOccupancy(table.getCurrOccupancy() + guest.getNumPax()); //add occupancy of new table
                    toUpdate.getGuestTable().setCurrOccupancy(toUpdate.getGuestTable().getCurrOccupancy() - toUpdate.getNumPax()); // where guest has a previous table
                    toUpdate.setGuestTable(table);
                }
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
                table.setCurrOccupancy(table.getCurrOccupancy() - guest.getNumPax());
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
    
    @Override
    public void updateGuestsRSVP(List<Guest> guests) throws InvalidUpdateException {
        //StatusEnum e = toType.equalsIgnoreCase("PENDING") ? StatusEnum.PENDING : toType.equalsIgnoreCase("NOTATTENDING") ? StatusEnum.NOTATTENDING : toType.equalsIgnoreCase("CONFIRMED")?  StatusEnum.CONFIRMED : StatusEnum.NOTSENT;
        for (Guest g : guests) {
            Guest managedGuest = em.find(Guest.class, g.getId());
            if (managedGuest != null) {
                managedGuest.setRsvp(g.getRsvp());
            } else {
                throw new InvalidUpdateException();
            }
        }

    }
    
    @Override
    public void updateGuestRSVP(String email, String rsvpStatus, Long weddingId) throws Throwable {
        String emailQuery = email;
        Guest g = (Guest) em.createQuery("SELECT g FROM Guest g JOIN g.weddingProject w WHERE g.email = :e AND w.weddingProjectId = :weddingId")
                .setParameter("e", emailQuery).setParameter("weddingId", weddingId).getResultStream()
                .findFirst().orElseThrow(() -> new InvalidUpdateException());
        g.setEmail(email);
        if (rsvpStatus.equalsIgnoreCase("NOTATTENDING")) {
            g.setRsvp(StatusEnum.NOTATTENDING);
        } else if (rsvpStatus.equalsIgnoreCase("CONFIRMED")) {
            g.setRsvp(StatusEnum.CONFIRMED);
        } else {
            throw new InvalidUpdateException();
        }
    }
    
    public Optional<Guest> getGuest(String email, Long weddingId) {
        System.out.println("EMAIL " + email);
        System.out.println("Wedding ID " + weddingId);
        if (weddingId != null && email != null) {
            WeddingProject project = em.find(WeddingProject.class, weddingId);
            if (project != null) {
                Optional<Guest> guest = em.createQuery("SELECT g FROM Guest g WHERE g.weddingProject.weddingProjectId = :wId AND g.email = :guestEmail")
                        .setParameter("wId", weddingId).setParameter("guestEmail", email).getResultStream().findFirst();
                return guest.map(g -> {
                    em.detach(g);
                    g.setWeddingProject(null);
                    g.setGuestTable(null);
                    return g;
                        });
            }
        }
        return Optional.empty();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
