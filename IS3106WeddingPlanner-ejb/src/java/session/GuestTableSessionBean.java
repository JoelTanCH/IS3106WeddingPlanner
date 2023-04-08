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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
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
    public Long createGuestTable(GuestTable guestTable, Long weddingProjectId) throws InvalidAssociationException {
        WeddingProject weddingProject = em.find(WeddingProject.class, weddingProjectId);
        if (weddingProject != null) {
            em.persist(guestTable);
            guestTable.setWeddingProject(weddingProject);
            weddingProject.getTables().add(guestTable);
            em.flush();
            return guestTable.getId();
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
            guestTable.setCurrOccupancy(guestTable.getCurrOccupancy() + 1);
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
            guestTable.setCurrOccupancy(guestTable.getCurrOccupancy() - 1);
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
        if (weddingId != null && em.find(WeddingProject.class, weddingId) != null) {
            Stream <GuestTable> tables = em.createQuery("SELECT g FROM GuestTable g WHERE g.weddingProject.weddingProjectId = ?1").setParameter(1, weddingId)
                                            .getResultStream();
            return tables.map(t -> {
                                em.detach(t);
                                t.getGuests().forEach(g -> em.detach(g));
                                return t;
                                }).collect(Collectors.toList());
        } else {
            throw new InvalidGetException();
        }
    }
    
    @Override
    public void updateGuestTables(List<GuestTable> tables, Long weddingId) throws InvalidAssociationException {
        if (weddingId == null || tables == null) {
            throw new InvalidAssociationException();
        }
        List<Long> tableIds = tables.stream().map(t -> t.getId()).collect(Collectors.toList()); //table ids of updated tables to insert

        if (!tableIds.isEmpty()) { //case where there are tables in the wedding
            try {
                List<Long> toDelete = em.createQuery("SELECT t.id FROM GuestTable t WHERE (t.id NOT IN :tableId AND t.weddingProject.weddingProjectId =:wId)").setParameter("tableId", tableIds).setParameter("wId", weddingId).getResultList();
                tables = tables.stream().filter(t -> !toDelete.contains(t.getId())).collect(Collectors.toList()); //tables that are not deleted
                toDelete.forEach(t -> {
                    try {
                        deleteGuestTable(t);
                    } catch (Exception e) {
                    }
                });

            } catch (Exception e) {
                //ignore, means that the JPQL query fails -> nothing to delete
            }
        } else { //case where there are no more tables in the wedding
            List<Long> gt = em.createQuery("SELECT t.id FROM GuestTable t WHERE t.weddingProject.weddingProjectId = :weddingId ").setParameter("weddingId", weddingId).getResultList();
            gt.forEach(t -> {
                try {
                    deleteGuestTable(t);
                } catch (InvalidDeleteException ex) {
                    Logger.getLogger(GuestTableSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        List<Guest> gst = em.createQuery("SELECT g FROM Guest g WHERE g.weddingProject.weddingProjectId = :id").setParameter("id", weddingId).getResultList();
        gst.forEach(g -> g.setGuestTable(null)); // runs within a JTA 
        List<GuestTable> guestTables = em.createQuery("SELECT t FROM GuestTable t WHERE t.weddingProject.weddingProjectId = :id").setParameter("id", weddingId).getResultList();
        guestTables.forEach(t -> t.getGuests().clear());
        
        tables.stream().forEach(table -> {
            List<Long> guestIds = table.getGuests().stream().map(guest -> guest.getId()).collect(Collectors.toList());
            if (guestIds.size() > 0) {
                List<Guest> guests = em.createQuery("SELECT g FROM Guest g WHERE g.id IN :ids").setParameter("ids", guestIds).getResultList();    //credits to chatgpt for debugging my jpql
                GuestTable t = em.find(GuestTable.class, table.getId());
                if (t != null) {
                    guests.forEach(g -> g.setGuestTable(t));
                    t.setGuests(guests);
                    t.setCurrOccupancy(table.getCurrOccupancy());
                    t.setLocationX(table.getLocationX());
                    t.setLocationY(table.getLocationY());
                    t.setTableNumber(table.getTableNumber());
                    t.setTableSize(table.getTableSize());
                }
            } else {
                GuestTable t = em.find(GuestTable.class, table.getId());
                if (t != null) {
                    t.setGuests(new ArrayList<>());
                    t.setCurrOccupancy(0);
                    t.setLocationX(table.getLocationX());
                    t.setLocationY(table.getLocationY());
                    t.setTableNumber(table.getTableNumber());
                    t.setTableSize(table.getTableSize());
                }
            }
        });
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
