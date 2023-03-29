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
import entity.Vendor;
import entity.WeddingProject;
import static enumeration.BrideGroomEnum.BRIDE;
import enumeration.CategoryEnum;
import static enumeration.StatusEnum.NOTSENT;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
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
import util.exception.InvalidAssociationException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import jwt.JWTSessionBeanLocal;
import jwt.KeyHolderLocal;
import session.VendorSessionBeanLocal;

/**
 *
 * @author leomi
 */
@Startup
@Singleton
public class TestingDataInitBean {

    @EJB(name = "VendorSessionBeanLocal")
    private VendorSessionBeanLocal vendorSessionBeanLocal;

    @EJB
    private AdminSessionBeanLocal adminSessionBean;

    @EJB
    private RequestSessionBeanLocal requestSessionBeanLocal;

    @EJB
    private GuestTableSessionBeanLocal guestTableSessionBean;

    @EJB
    private GuestSessionBeanLocal guestSessionBean;

    @EJB
    private JWTSessionBeanLocal jwtSBL;

    @EJB
    private KeyHolderLocal keyHolderSBL;

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;

    public TestingDataInitBean() {
    }

    @PostConstruct
    public void init() {
        if (em.find(WeddingProject.class, 1L) == null) {
            try {
                WeddingProject weddingProject = new WeddingProject();
                em.persist(weddingProject);
                em.flush();
                Guest guest = new Guest();
                guest.setAttendingSide(BRIDE);
                guest.setEmail("RANDOM@EMAIL.COM");
                guest.setName("TEST");
                guest.setNumPax(1);
                guest.setRsvp(NOTSENT);
                guestSessionBean.createGuest(guest, weddingProject.getWeddingProjectId());
                GuestTable guestTable = new GuestTable();
                guestTable.setCapacity(10);
                guestTable.setCurrOccupancy(0);
                guestTable.setLocationX(0);
                guestTable.setLocationY(0);
                guestTable.setTableNumber(1);
                guestTable.setTableSize(100);
                guestTableSessionBean.createGuestTable(guestTable, 1L);
            } catch (InvalidAssociationException ex) {
                //Logger.getLogger(TestingDataInitBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        keyHolderSBL.setKey(key);
        String token = jwtSBL.generateToken("ADMIN");
        System.out.println(token);
        System.out.println(jwtSBL.verifyToken(token));

        if (em.find(Admin.class, (long) 1) == null) {
            Admin a1 = new Admin();
            a1.setEmail("joestar@gmail.com");
            a1.setUsername("Joseph");
            a1.setPassword("caesar");
            adminSessionBean.createAdmin(a1);

            Vendor vendor = new Vendor();
            vendor.setUsername("TestVendor");
            vendor.setEmail("sampleVendorEmail@email.com");
            vendor.setPassword("password");
            vendor.setDescription("Sample vendor description. I do these stuff");
            vendor.setBanner("This is a banner");
            vendor.setWebsiteUrl("vendorURL.com");
            vendor.setInstagramUrl("This is the URL of instagram");
            vendor.setFacebookUrl("Facebook url");
            vendor.setWhatsappUrl("Whatsapp url");
            vendor.setCategory(CategoryEnum.ENTERTAINMENT);

            Request sampleRequest = new Request();
            sampleRequest.setIsAccepted(null);
            sampleRequest.setQuotationURL("www.fakeUrl.com");
            sampleRequest.setQuotedPrice(BigDecimal.valueOf(1000L));
            sampleRequest.setRequestDate(new Date());
            sampleRequest.setRequestDetails("Do something for me");
            sampleRequest.setVendor(vendor);
            vendor.getRequests().add(sampleRequest);
            requestSessionBeanLocal.createRequest(sampleRequest);
            sampleRequest = new Request();
            sampleRequest.setIsAccepted(null);
            sampleRequest.setQuotationURL("www.anotherfakeUrl.com");
            sampleRequest.setQuotedPrice(BigDecimal.valueOf(200L));
            sampleRequest.setRequestDate(new Date());
            sampleRequest.setRequestDetails("Small gig");
            sampleRequest.setVendor(vendor);
            vendor.getRequests().add(sampleRequest);
            requestSessionBeanLocal.createRequest(sampleRequest);
            vendorSessionBeanLocal.createVendor(vendor);
        }
    }
}
