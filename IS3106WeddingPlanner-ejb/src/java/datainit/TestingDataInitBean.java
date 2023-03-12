/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datainit;

import entity.Guest;
import entity.GuestTable;
import entity.WeddingProject;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.GuestSessionBeanLocal;
import session.GuestTableSessionBeanLocal;

/**
 *
 * @author leomi
 */
@Startup
@Singleton
public class TestingDataInitBean {

    @EJB
    private GuestTableSessionBeanLocal guestTableSessionBean;

    @EJB
    private GuestSessionBeanLocal guestSessionBean;

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;

    @PostConstruct
    public void init() {
        if (em.find(WeddingProject.class, 1L) == null) {
            WeddingProject weddingProject = new WeddingProject();
            em.persist(weddingProject);
            em.flush();
            Guest guest = new Guest();
            guest.setAttendingSide("BRIDE");
            guest.setEmail("RANDOM@EMAIL.COM");
            guest.setName("TEST");
            guest.setNumPax(1);
            guest.setRsvp(false);
            guestSessionBean.createGuest(guest, weddingProject.getWeddingProjectId());
            GuestTable guestTable = new GuestTable();
            guestTable.setCapacity(10);
            guestTable.setCurrOccupancy(0);
            guestTable.setLocationX(0);
            guestTable.setLocationY(0);
            guestTable.setTableNumber(1);
            guestTable.setTableSize(100);
            guestTableSessionBean.createGuestTable(guestTable, 1L);
       }
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
