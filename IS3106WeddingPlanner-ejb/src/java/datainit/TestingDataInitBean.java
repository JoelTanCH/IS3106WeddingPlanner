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
import entity.WeddingBudgetItem;
import entity.WeddingBudgetList;
import entity.WeddingItinerary;
import entity.WeddingOrganiser;
import entity.WeddingProject;
import static enumeration.BrideGroomEnum.BRIDE;
import enumeration.CategoryEnum;
import static enumeration.StatusEnum.NOTSENT;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.math.BigDecimal;
import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import jwt.JWTSessionBeanLocal;
import jwt.KeyHolderLocal;
import session.VendorSessionBeanLocal;
import session.WeddingBudgetSessionBeanLocal;
import session.WeddingItinerarySessionBeanLocal;
import session.WeddingOrganiserSessionBeanLocal;
import session.WeddingProjectSessionBeanLocal;

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
    private WeddingProjectSessionBeanLocal weddingProjectSessionBeanLocal;

    @EJB
    private WeddingOrganiserSessionBeanLocal weddingOrganiserSessionBeanLocal;

    @EJB
    private WeddingBudgetSessionBeanLocal weddingBudgetSessionBean;

    @EJB
    private WeddingItinerarySessionBeanLocal weddingItinerarySessionBean;

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

        Key keyGenerated = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        keyHolderSBL.setKey(keyGenerated);
        String token = jwtSBL.generateToken("ADMIN");
        System.out.println(token);
        System.out.println(jwtSBL.verifyToken(token));

        if (em.find(Admin.class, (long) 1) == null) {
            Admin a1 = new Admin();
            a1.setEmail("joestar@gmail.com");
            a1.setUsername("Joseph");
            a1.setPassword("caesar");
            adminSessionBean.createAdmin(a1);

            WeddingOrganiser w1 = new WeddingOrganiser();
            w1.setEmail("weddingOrganiser1@email.com");
            w1.setUsername("weddingOrganiser1");
            w1.setPassword("password");
            weddingOrganiserSessionBeanLocal.createWeddingOrganiser(w1);
            WeddingOrganiser w2 = new WeddingOrganiser();
            w2.setEmail("weddingOrganiser2@email.com");
            w2.setUsername("weddingOrganiser2");
            w2.setPassword("password");
            weddingOrganiserSessionBeanLocal.createWeddingOrganiser(w2);
            em.flush();

            if (em.find(WeddingProject.class, 1L) == null) {
                try {

                    String startDateString = "2023-03-04 1330";
                    String endDateString = "2023-03-04 1830";
                    String startDateString2 = "2023-05-04 1230";
                    String endDateString2 = "2023-05-04 1530";

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HHmm");
                    Date startDate = null;
                    Date endDate = null;
                    Date startDate2 = null;
                    Date endDate2 = null;
                    try {
                        startDate = dateFormat.parse(startDateString);
                        endDate = dateFormat.parse(endDateString);
                        startDate2 = dateFormat.parse(startDateString2);
                        endDate2 = dateFormat.parse(endDateString2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    WeddingProject weddingProject1 = new WeddingProject();
                    weddingProject1.setName("weddingProject1");
                    weddingProject1.setDescription("description for project1");
                    weddingProject1.setCompleted(Boolean.FALSE);
                    weddingProject1.setWeddingOrganiser(w1);
                    weddingProject1.setWeddingDate(startDate); // since the database only stores date anyway, we can just use the same date for both event & startTime
                    weddingProject1.setWeddingStartTime(startDate);
                    weddingProject1.setWeddingEndTime(endDate);
                    weddingProject1.setVenue("Venue oneeeee");
                    weddingProjectSessionBeanLocal.createWeddingProject(w1.getUserId(), weddingProject1);

                    WeddingProject weddingProject2 = new WeddingProject();
                    weddingProject2.setName("weddingProject2");
                    weddingProject2.setDescription("description for project2");
                    weddingProject2.setCompleted(Boolean.TRUE);
                    weddingProject2.setWeddingOrganiser(w1);
                    weddingProject2.setWeddingDate(startDate); // since the database only stores date anyway, we can just use the same date for both event & startTime
                    weddingProject2.setWeddingStartTime(startDate2);
//                    weddingProject2.setWeddingEndTime(endDate2); // testing what if wedding doesnt have endDate
                    weddingProject2.setVenue("Venue zweiiii");
                    weddingProjectSessionBeanLocal.createWeddingProject(w1.getUserId(), weddingProject2);

                    WeddingProject weddingProject3 = new WeddingProject();
                    weddingProject3.setName("weddingProject3");
                    weddingProject3.setDescription("description for project3, this belongs to wedding-organiser with id of 10 i think");
                    weddingProject3.setCompleted(Boolean.FALSE);
                    weddingProject3.setWeddingOrganiser(w2);
                    weddingProject3.setWeddingDate(startDate); // since the database only stores date anyway, we can just use the same date for both event & startTime
                    weddingProject3.setWeddingStartTime(startDate);
                    weddingProject3.setWeddingEndTime(endDate);
                    weddingProject3.setVenue("Venue threeeeee");
                    weddingProjectSessionBeanLocal.createWeddingProject(w2.getUserId(), weddingProject3);
                    em.flush();

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
                    sampleRequest.setQuotedPrice(null);
                    sampleRequest.setRequestDate(new Date());
                    sampleRequest.setRequestDetails("Do something for me");
                    sampleRequest.setVendor(vendor_entertainment);
                    sampleRequest.setWeddingProject(weddingProject2);
                    vendor_entertainment.getRequests().add(sampleRequest);
                    requestSessionBeanLocal.createRequest(sampleRequest);
                    sampleRequest = new Request();
                    sampleRequest.setIsAccepted(null);
                    sampleRequest.setQuotationURL("www.anotherfakeUrl.com");
                    sampleRequest.setQuotedPrice(null);
                    sampleRequest.setRequestDate(new Date());
                    sampleRequest.setRequestDetails("Small gig");
                    sampleRequest.setVendor(vendor_entertainment);
                    sampleRequest.setWeddingProject(weddingProject3);
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

                    Guest guest = new Guest();
                    guest.setAttendingSide(BRIDE);
                    guest.setEmail("RANDOM@EMAIL.COM");
                    guest.setName("TEST");
                    guest.setNumPax(1);
                    guest.setRsvp(NOTSENT);
                    guestSessionBean.createGuest(guest, weddingProject1.getWeddingProjectId());
                    GuestTable guestTable = new GuestTable();
                    guestTable.setCapacity(10);
                    guestTable.setCurrOccupancy(0);
                    guestTable.setLocationX(0);
                    guestTable.setLocationY(0);
                    guestTable.setTableNumber(1);
                    guestTable.setTableSize(200);
                    guestTableSessionBean.createGuestTable(guestTable, 1L);

                    WeddingItinerary weddingItinerary = new WeddingItinerary();
                    weddingItinerary.setEventName("Sample Event");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    weddingItinerary.setEventDate(formatter.parse("2023-04-04"));
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(weddingItinerary.getEventDate());
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date eventStartTime = formatter.parse(String.format("%04d-%02d-%02d 20:00", year, month + 1, day));
                    Date eventEndTime = formatter.parse(String.format("%04d-%02d-%02d 22:00", year, month + 1, day));
                    weddingItinerary.setEventStartTime(eventStartTime);
                    weddingItinerary.setEventEndTime(eventEndTime);
//                formatter = new SimpleDateFormat("HH:mm");
//                weddingItinerary.setEventStartTime(formatter.parse("20:00"));
//                weddingItinerary.setEventEndTime(formatter.parse("22:00"));
                    weddingItinerarySessionBean.createNewItinerary(weddingItinerary, weddingProject1.getWeddingProjectId());

                    WeddingBudgetList budget = new WeddingBudgetList();
                    budget.setBudget(BigDecimal.valueOf(10000));
                    weddingBudgetSessionBean.createBudget(budget, weddingProject1.getWeddingProjectId());
                    em.persist(budget);
                    em.flush();
                    // Sample 1
                    WeddingBudgetItem item = new WeddingBudgetItem();
                    item.setName("Sample 1");
                    item.setCost(BigDecimal.valueOf(2000));
                    item.setIsPaid(true);
                    item.setCategory(CategoryEnum.FOOD);
                    weddingBudgetSessionBean.createItem(item, budget.getWeddingBudgetListId());
                    // Sample 2
                    item = new WeddingBudgetItem();
                    item.setName("Sample 2");
                    item.setCost(BigDecimal.valueOf(2500));
                    item.setIsPaid(false);
                    item.setCategory(CategoryEnum.DECORATION);
                    weddingBudgetSessionBean.createItem(item, budget.getWeddingBudgetListId());
                    // Sample 3
                    item = new WeddingBudgetItem();
                    item.setName("Sample 3");
                    item.setCost(BigDecimal.valueOf(1500));
                    item.setIsPaid(false);
                    item.setCategory(CategoryEnum.DECORATION);
                    weddingBudgetSessionBean.createItem(item, budget.getWeddingBudgetListId());
                } catch (InvalidAssociationException ex) {
                    //Logger.getLogger(TestingDataInitBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {

                }

            }

        }

        // TESTING VENDOR 2'S LIST OF REQUEST
        try {
            Vendor vendor2 = vendorSessionBeanLocal.getVendorByVendorName("EntertainmentVendor");

            System.out.println("vendor2's request list size" + vendor2.getRequests().size());
        } catch (Exception e) {
            // do nothing
        }
    }
}
