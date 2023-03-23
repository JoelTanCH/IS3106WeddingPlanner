/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Guest;
import entity.GuestTable;
import entity.WeddingProject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 *
 * @author leomi
 */
import util.exception.InvalidAssociationException;
import util.exception.InvalidDeleteException;
import util.exception.InvalidGetException;
@Stateless
public class GuestTableSessionBean implements GuestTableSessionBeanLocal {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;

    @Override
    public void createGuestTable(GuestTable guestTable, Long weddingProjectId) throws InvalidAssociationException {
        WeddingProject weddingProject = em.find(WeddingProject.class, weddingProjectId);
        if (weddingProject != null) {
            em.persist(guestTable);
            guestTable.setWeddingProject(weddingProject);
            weddingProject.getTables().add(guestTable);
        } else {
            throw new InvalidAssociationException();
        }
    }
    
    @Override
    public void addGuestToTable(Long guestId, Long guestTableId) throws InvalidAssociationException {
        Guest guest = em.find(Guest.class, guestId);
        GuestTable guestTable = em.find(GuestTable.class, guestTableId);
        if (guest != null && guestTable != null) {
            if (!guest.getWeddingProject().equals(guestTable.getWeddingProject())) {
                throw new InvalidAssociationException();
            }
            guest.setGuestTable(guestTable);
            guestTable.getGuests().add(guest);
        } else {
            throw new InvalidAssociationException();
        }
    }
    
    @Override
    public void removeGuestFromTable(Long guestId, Long guestTableId) throws InvalidAssociationException {
        Guest guest = em.find(Guest.class, guestId);
        GuestTable guestTable = em.find(GuestTable.class, guestTableId);
        if (guest != null && guestTable != null) {
            if (!guest.getWeddingProject().equals(guestTable.getWeddingProject())) {
                throw new InvalidAssociationException();
            }
            guest.setGuestTable(null);
            guestTable.getGuests().remove(guest);
        } else {
            throw new InvalidAssociationException();
        }
    }
    
    @Override
    public void deleteGuestTable(Long guestTableId) throws InvalidDeleteException {
        GuestTable guestTable = em.find(GuestTable.class, guestTableId);
        if (guestTable != null) {
            WeddingProject weddingProject = guestTable.getWeddingProject();
            if (!guestTable.getGuests().isEmpty()) {
                List<Guest> guests = guestTable.getGuests();
                guests.forEach(guest -> guest.setGuestTable(null));
                guestTable.getGuests().clear();
            }
            weddingProject.getTables().remove(guestTable);
            em.remove(guestTable);
        } else {
            throw new InvalidDeleteException();
        }
    }
    
    @Override
    public List<GuestTable> getGuestTables(Long weddingId) throws InvalidGetException {
        if (em.find(WeddingProject.class, weddingId) != null) {
            Stream <GuestTable> tables = em.createQuery("SELECT g FROM GuestTable g WHERE g.weddingProject.weddingProjectId = ?1").setParameter(1, weddingId)
                                            .getResultStream();
            return tables.map(g -> {
                                em.detach(g);
                                return g;
                                }).collect(Collectors.toList());
        } else {
            throw new InvalidGetException();
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
