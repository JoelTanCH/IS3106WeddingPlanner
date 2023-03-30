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

            Vendor vendor_entertainment = new Vendor();
            vendor_entertainment.setUsername("EntertainmentVendor");
            vendor_entertainment.setEmail("sampleVendorEmail@email.com");
            vendor_entertainment.setPassword("password");
            vendor_entertainment.setDescription("Sample vendor description. I do these stuff");
            vendor_entertainment.setBanner("This is a banner");
            vendor_entertainment.setWebsiteUrl("vendorURL.com");
            vendor_entertainment.setInstagramUrl("This is the URL of instagram");
            vendor_entertainment.setFacebookUrl("Facebook url");
            vendor_entertainment.setWhatsappUrl("Whatsapp url");
            vendor_entertainment.setCategory(CategoryEnum.ENTERTAINMENT);

            Request sampleRequest = new Request();
            sampleRequest.setIsAccepted(null);
            sampleRequest.setQuotationURL("www.fakeUrl.com");
            sampleRequest.setQuotedPrice(BigDecimal.valueOf(1000L));
            sampleRequest.setRequestDate(new Date());
            sampleRequest.setRequestDetails("Do something for me");
            sampleRequest.setVendor(vendor_entertainment);
            vendor_entertainment.getRequests().add(sampleRequest);
            requestSessionBeanLocal.createRequest(sampleRequest);
            sampleRequest = new Request();
            sampleRequest.setIsAccepted(null);
            sampleRequest.setQuotationURL("www.anotherfakeUrl.com");
            sampleRequest.setQuotedPrice(BigDecimal.valueOf(200L));
            sampleRequest.setRequestDate(new Date());
            sampleRequest.setRequestDetails("Small gig");
            sampleRequest.setVendor(vendor_entertainment);
            vendor_entertainment.getRequests().add(sampleRequest);
            requestSessionBeanLocal.createRequest(sampleRequest);
            vendorSessionBeanLocal.createVendor(vendor_entertainment);
            
            Vendor vendor_entertainment2 = new Vendor();
            vendor_entertainment2.setUsername("EntertainmentVendor2");
            vendor_entertainment2.setEmail("EntertainmentVendor2@email.com");
            vendor_entertainment2.setPassword("password");
            vendor_entertainment2.setDescription("Sample vendor description. I do these stuff");
            vendor_entertainment2.setBanner("This is a banner");
            vendor_entertainment2.setWebsiteUrl("vendorURL.com");
            vendor_entertainment2.setInstagramUrl("This is the URL of instagram");
            vendor_entertainment2.setFacebookUrl("Facebook url");
            vendor_entertainment2.setWhatsappUrl("Whatsapp url");
            vendor_entertainment2.setCategory(CategoryEnum.ENTERTAINMENT);
            vendorSessionBeanLocal.createVendor(vendor_entertainment2);
            
            Vendor vendor_food = new Vendor();
            vendor_food.setUsername("FoodVendor");
            vendor_food.setEmail("FoodVendor@email.com");
            vendor_food.setPassword("password");
            vendor_food.setDescription("Sample vendor description. I do these stuff");
            vendor_food.setBanner("This is a banner");
            vendor_food.setWebsiteUrl("vendorURL.com");
            vendor_food.setInstagramUrl("This is the URL of instagram");
            vendor_food.setFacebookUrl("Facebook url");
            vendor_food.setWhatsappUrl("Whatsapp url");
            vendor_food.setCategory(CategoryEnum.FOOD);
            vendorSessionBeanLocal.createVendor(vendor_food);
            
            Vendor vendor_lighting = new Vendor();
            vendor_lighting.setUsername("LightingVendor");
            vendor_lighting.setEmail("LightingVendor@email.com");
            vendor_lighting.setPassword("password");
            vendor_lighting.setDescription("Sample vendor description. I do these stuff");
            vendor_lighting.setBanner("This is a banner");
            vendor_lighting.setWebsiteUrl("vendorURL.com");
            vendor_lighting.setInstagramUrl("This is the URL of instagram");
            vendor_lighting.setFacebookUrl("Facebook url");
            vendor_lighting.setWhatsappUrl("Whatsapp url");
            vendor_lighting.setCategory(CategoryEnum.LIGHTING);
            vendorSessionBeanLocal.createVendor(vendor_lighting);
            
            Vendor vendor_decoration = new Vendor();
            vendor_decoration.setUsername("DecorationVendor");
            vendor_decoration.setEmail("DecorationVendor@email.com");
            vendor_decoration.setPassword("password");
            vendor_decoration.setDescription("Sample vendor description. I do these stuff");
            vendor_decoration.setBanner("This is a banner");
            vendor_decoration.setWebsiteUrl("vendorURL.com");
            vendor_decoration.setInstagramUrl("This is the URL of instagram");
            vendor_decoration.setFacebookUrl("Facebook url");
            vendor_decoration.setWhatsappUrl("Whatsapp url");
            vendor_decoration.setCategory(CategoryEnum.DECORATION);
            vendorSessionBeanLocal.createVendor(vendor_decoration);
            
            Vendor vendor_clothes = new Vendor();
            vendor_clothes.setUsername("ClothesVendor");
            vendor_clothes.setEmail("ClothesVendor@email.com");
            vendor_clothes.setPassword("password");
            vendor_clothes.setDescription("Sample vendor description. I do these stuff");
            vendor_clothes.setBanner("This is a banner");
            vendor_clothes.setWebsiteUrl("vendorURL.com");
            vendor_clothes.setInstagramUrl("This is the URL of instagram");
            vendor_clothes.setFacebookUrl("Facebook url");
            vendor_clothes.setWhatsappUrl("Whatsapp url");
            vendor_clothes.setCategory(CategoryEnum.CLOTHES);
            vendorSessionBeanLocal.createVendor(vendor_clothes);
            
            Vendor vendor_venue = new Vendor();
            vendor_venue.setUsername("VenueVendor");
            vendor_venue.setEmail("VenueVendor@email.com");
            vendor_venue.setPassword("password");
            vendor_venue.setDescription("Sample vendor description. I do these stuff");
            vendor_venue.setBanner("This is a banner");
            vendor_venue.setWebsiteUrl("vendorURL.com");
            vendor_venue.setInstagramUrl("This is the URL of instagram");
            vendor_venue.setFacebookUrl("Facebook url");
            vendor_venue.setWhatsappUrl("Whatsapp url");
            vendor_venue.setCategory(CategoryEnum.VENUE);
            vendorSessionBeanLocal.createVendor(vendor_venue);
        }
    }
}
