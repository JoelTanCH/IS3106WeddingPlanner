/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datainit;

import entity.Admin;
import entity.Guest;
import entity.GuestTable;
import entity.Request;
import entity.WeddingProject;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.AdminSessionBeanLocal;
import session.GuestSessionBeanLocal;
import session.GuestTableSessionBeanLocal;
import session.RequestSessionBeanLocal;

/**
 *
 * @author leomi
 */
@Startup
@Singleton
public class TestingDataInitBean {

    @EJB
    private AdminSessionBeanLocal adminSessionBean;

    @EJB
    private RequestSessionBeanLocal requestSessionBeanLocal;

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
        
        if (em.find(Admin.class, (long) 1) == null) {
            Admin a1 = new Admin();
            a1.setEmail("joestar@gmail.com");
            a1.setUsername("Joseph");
            a1.setPassword("caesar");
            adminSessionBean.createAdmin(a1);

            Request sampleRequest = new Request();
            sampleRequest.setIsAccepted(false);
            sampleRequest.setQuotationURL("www.fakeUrl.com");
            sampleRequest.setQuotedPrice(BigDecimal.valueOf(1000L));
            sampleRequest.setRequestDate(new Date());
            sampleRequest.setRequestDetails("Do something for me");
            requestSessionBeanLocal.createRequest(sampleRequest);

        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
