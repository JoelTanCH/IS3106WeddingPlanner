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
import entity.Transaction;
import entity.Vendor;
import entity.WeddingBudgetItem;
import entity.WeddingBudgetList;
import entity.WeddingChecklist;
import entity.WeddingItinerary;
import entity.WeddingOrganiser;
import entity.WeddingParentTask;
import entity.WeddingProject;
import entity.WeddingSubtask;
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
import session.TransactionSessionBeanLocal;
import session.VendorSessionBeanLocal;
import session.WeddingBudgetSessionBeanLocal;
import session.WeddingChecklistBeanLocal;
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
    private TransactionSessionBeanLocal transactionSessionBeanLocal;
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
    private WeddingChecklistBeanLocal weddingChecklistSessionBean;

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
            em.flush(); // need this so it ensures Admin's id is 1

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

                    String startDateString = "2023-06-06 1330";
                    String endDateString = "2023-06-06 1830";
                    String startDateString2 = "2023-05-04 1230";
                    String endDateString2 = "2023-05-04 1530";
                    String startDateString3 = "2023-07-06 1330";
                    String endDateString3 = "2023-07-06 1830";
                    String startDateString4 = "2023-08-04 1230";
                    String endDateString4 = "2023-08-04 1530";

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HHmm");
                    Date startDate = null;
                    Date endDate = null;
                    Date startDate2 = null;
                    Date endDate2 = null;
                    Date startDate3 = null;
                    Date endDate3 = null;
                    Date startDate4 = null;
                    Date endDate4 = null;
                    try {
                        startDate = dateFormat.parse(startDateString);
                        endDate = dateFormat.parse(endDateString);
                        startDate2 = dateFormat.parse(startDateString2);
                        endDate2 = dateFormat.parse(endDateString2);
                        startDate3 = dateFormat.parse(startDateString3);
                        endDate3 = dateFormat.parse(endDateString3);
                        startDate4 = dateFormat.parse(startDateString4);
                        endDate4 = dateFormat.parse(endDateString4);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    WeddingProject weddingProject1 = new WeddingProject();
                    weddingProject1.setName("Bob and Alice's Wedding");
                    weddingProject1.setDescription("description for project1");
                    weddingProject1.setCompleted(Boolean.FALSE);
                    weddingProject1.setWeddingOrganiser(w1);
                    weddingProject1.setWeddingDate(startDate); // since the database only stores date anyway, we can just use the same date for both event & startTime
                    weddingProject1.setWeddingStartTime(startDate);
                    weddingProject1.setWeddingEndTime(endDate);
                    weddingProject1.setVenue("Hotel Luxury Marina");
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
                    
                    WeddingProject weddingProject4 = new WeddingProject();
                    weddingProject4.setName("Thomas and Susan's Wedding");
                    weddingProject4.setDescription("Thomas and Susan's Wedding!");
                    weddingProject4.setCompleted(Boolean.FALSE);
                    weddingProject4.setWeddingOrganiser(w1);
                    weddingProject4.setWeddingDate(startDate3); // since the database only stores date anyway, we can just use the same date for both event & startTime
                    weddingProject4.setWeddingStartTime(startDate3);
                    weddingProject4.setWeddingEndTime(endDate3);
                    weddingProject4.setVenue("Hotel Luxury Marina");
                    weddingProjectSessionBeanLocal.createWeddingProject(w1.getUserId(), weddingProject4);
                    
                    WeddingProject weddingProject5 = new WeddingProject();
                    weddingProject5.setName("Alex and Haley's Wedding");
                    weddingProject5.setDescription("Alex and Haley's Wedding!");
                    weddingProject5.setCompleted(Boolean.FALSE);
                    weddingProject5.setWeddingOrganiser(w1);
                    weddingProject5.setWeddingDate(startDate4); // since the database only stores date anyway, we can just use the same date for both event & startTime
                    weddingProject5.setWeddingStartTime(startDate4);
                    weddingProject5.setWeddingEndTime(endDate4);
                    weddingProject5.setVenue("Hotel Luxury Marina");
                    weddingProjectSessionBeanLocal.createWeddingProject(w1.getUserId(), weddingProject5);
                    
                    em.flush();

                    Vendor vendor_entertainment = new Vendor();
                    vendor_entertainment.setUsername("EntertainmentVendor");
                    vendor_entertainment.setEmail("sampleVendorEmail@email.com");
                    vendor_entertainment.setPassword("password");
                    vendor_entertainment.setDescription("Are you looking for the perfect way to make your wedding day even more memorable? Look no further than hiring a professional wedding entertainer!");
                    vendor_entertainment.setWebsiteUrl("vendorURL.com");
                    vendor_entertainment.setInstagramUrl("This is the URL of instagram");
                    vendor_entertainment.setFacebookUrl("Facebook url");
                    vendor_entertainment.setWhatsappUrl("Whatsapp url");
                    vendor_entertainment.setCategory(CategoryEnum.ENTERTAINMENT);

                    Request sampleRequest = new Request();
                    sampleRequest.setIsAccepted(null);
                    sampleRequest.setQuotationURL("www.fakeUrl.com");
                    sampleRequest.setQuotedPrice(null);
                    Date date = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.DATE, 2);
                    Date newDate = calendar.getTime();
                    sampleRequest.setRequestDate(newDate);
                    sampleRequest.setRequestDetails("Do something for me");
                    sampleRequest.setVendor(vendor_entertainment);
                    sampleRequest.setWeddingProject(weddingProject1);
                    vendor_entertainment.getRequests().add(sampleRequest);
                    requestSessionBeanLocal.createRequest(sampleRequest);
                    
                    sampleRequest = new Request();
                    sampleRequest.setIsAccepted(true);
                    sampleRequest.setQuotationURL("www.anotherfakeUrl.com");
                    sampleRequest.setQuotedPrice(BigDecimal.ONE);
                    sampleRequest.setRequestDate(new Date());
                    sampleRequest.setRequestDetails("Small gig");
                    sampleRequest.setVendor(vendor_entertainment);
                    sampleRequest.setWeddingProject(weddingProject2);

                    Transaction sampleTransaction = new Transaction();
                    sampleTransaction.setIsPaid(false);
                    sampleTransaction.setRequest(sampleRequest);
                    sampleTransaction.setTotalPrice(BigDecimal.ONE);
                    sampleTransaction.setTransactionTime(new Date());
                    sampleRequest.setTransaction(sampleTransaction);

                    vendor_entertainment.getRequests().add(sampleRequest);
                    requestSessionBeanLocal.createRequest(sampleRequest);
                    transactionSessionBeanLocal.createTransaction(sampleTransaction);
                    //vendorSessionBeanLocal.createVendor(vendor_entertainment);
                    
                    Request sampleRequest4 = new Request();
                    sampleRequest4.setIsAccepted(null);
                    sampleRequest4.setQuotationURL("www.fakeUrl.com");
                    sampleRequest4.setQuotedPrice(null);
                    //Date date = new Date();
                    //Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.DATE, 3);
                    Date newDate4 = calendar.getTime();
                    sampleRequest4.setRequestDate(newDate4);
                    sampleRequest4.setRequestDetails("Do something for me");
                    sampleRequest4.setVendor(vendor_entertainment);
                    sampleRequest4.setWeddingProject(weddingProject4);
                    vendor_entertainment.getRequests().add(sampleRequest4);
                    requestSessionBeanLocal.createRequest(sampleRequest4);
                    
                    Transaction sampleTransaction4 = new Transaction();
                    sampleTransaction4.setIsPaid(false);
                    sampleTransaction4.setRequest(sampleRequest4);
                    sampleTransaction4.setTotalPrice(BigDecimal.ONE);
                    sampleTransaction4.setTransactionTime(new Date());
                    sampleRequest4.setTransaction(sampleTransaction4);

                    vendor_entertainment.getRequests().add(sampleRequest4);
                    requestSessionBeanLocal.createRequest(sampleRequest4);
                    transactionSessionBeanLocal.createTransaction(sampleTransaction4);
                    
                    Request sampleRequest5 = new Request();
                    sampleRequest5.setIsAccepted(null);
                    sampleRequest5.setQuotationURL("www.fakeUrl.com");
                    sampleRequest5.setQuotedPrice(null);
                    //Date date = new Date();
                    //Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.DATE, 5);
                    Date newDate5 = calendar.getTime();
                    sampleRequest5.setRequestDate(newDate5);
                    sampleRequest5.setRequestDetails("Do something for me");
                    sampleRequest5.setVendor(vendor_entertainment);
                    sampleRequest5.setWeddingProject(weddingProject5);
                    vendor_entertainment.getRequests().add(sampleRequest5);
                    requestSessionBeanLocal.createRequest(sampleRequest5);
                    
                    Transaction sampleTransaction5 = new Transaction();
                    sampleTransaction5.setIsPaid(false);
                    sampleTransaction5.setRequest(sampleRequest5);
                    sampleTransaction5.setTotalPrice(BigDecimal.ONE);
                    sampleTransaction5.setTransactionTime(new Date());
                    sampleRequest5.setTransaction(sampleTransaction5);

                    vendor_entertainment.getRequests().add(sampleRequest5);
                    requestSessionBeanLocal.createRequest(sampleRequest5);
                    transactionSessionBeanLocal.createTransaction(sampleTransaction5);
                    vendorSessionBeanLocal.createVendor(vendor_entertainment);

                    Vendor vendor_entertainment2 = new Vendor();
                    vendor_entertainment2.setUsername("EntertainmentVendor2");
                    vendor_entertainment2.setEmail("EntertainmentVendor2@email.com");
                    vendor_entertainment2.setPassword("password");
                    vendor_entertainment2.setDescription("Sample vendor description. I do these stuff");
                    vendor_entertainment2.setWebsiteUrl("vendorURL.com");
                    vendor_entertainment2.setInstagramUrl("This is the URL of instagram");
                    vendor_entertainment2.setFacebookUrl("Facebook url");
                    vendor_entertainment2.setWhatsappUrl("Whatsapp url");
                    vendor_entertainment2.setCategory(CategoryEnum.ENTERTAINMENT);
                    vendorSessionBeanLocal.createVendor(vendor_entertainment2);

                    Vendor BestWeddingEntertainer = new Vendor();
                    BestWeddingEntertainer.setUsername("BestWeddingEntertainer");
                    BestWeddingEntertainer.setEmail("BestWeddingEntertainer@email.com");
                    BestWeddingEntertainer.setPassword("password");
                    BestWeddingEntertainer.setDescription("Make your wedding day unforgettable with a professional wedding entertainer. From DJs to live bands to magicians, they bring the fun and excitement, leaving you and your guests with memories that will last a lifetime.");
                    BestWeddingEntertainer.setWebsiteUrl("vendorURL.com");
                    BestWeddingEntertainer.setInstagramUrl("This is the URL of instagram");
                    BestWeddingEntertainer.setFacebookUrl("Facebook url");
                    BestWeddingEntertainer.setWhatsappUrl("Whatsapp url");
                    BestWeddingEntertainer.setCategory(CategoryEnum.ENTERTAINMENT);
                    vendorSessionBeanLocal.createVendor(BestWeddingEntertainer);

                    Vendor KindaEntertaining = new Vendor();
                    KindaEntertaining.setUsername("KindaEntertaining");
                    KindaEntertaining.setEmail("KindaEntertaining@email.com");
                    KindaEntertaining.setPassword("password");
                    KindaEntertaining.setDescription("Make your wedding day unforgettable with a professional wedding entertainer. From DJs to live bands to magicians, they bring the fun and excitement, leaving you and your guests with memories that will last a lifetime.");
                    KindaEntertaining.setWebsiteUrl("vendorURL.com");
                    KindaEntertaining.setInstagramUrl("This is the URL of instagram");
                    KindaEntertaining.setFacebookUrl("Facebook url");
                    KindaEntertaining.setWhatsappUrl("Whatsapp url");
                    KindaEntertaining.setCategory(CategoryEnum.ENTERTAINMENT);
                    vendorSessionBeanLocal.createVendor(KindaEntertaining);

                    Vendor UnforgettableEntertainment = new Vendor();
                    UnforgettableEntertainment.setUsername("UnforgettableEntertainment");
                    UnforgettableEntertainment.setEmail("UnforgettableEntertainment@email.com");
                    UnforgettableEntertainment.setPassword("password");
                    UnforgettableEntertainment.setDescription("Make your wedding day unforgettable with a professional wedding entertainer. From DJs to live bands to magicians, they bring the fun and excitement, leaving you and your guests with memories that will last a lifetime.");
                    UnforgettableEntertainment.setWebsiteUrl("vendorURL.com");
                    UnforgettableEntertainment.setInstagramUrl("This is the URL of instagram");
                    UnforgettableEntertainment.setFacebookUrl("Facebook url");
                    UnforgettableEntertainment.setWhatsappUrl("Whatsapp url");
                    UnforgettableEntertainment.setCategory(CategoryEnum.ENTERTAINMENT);
                    vendorSessionBeanLocal.createVendor(UnforgettableEntertainment);

                    Vendor EverlastingMemories = new Vendor();
                    EverlastingMemories.setUsername("EverlastingMemories");
                    EverlastingMemories.setEmail("EverlastingMemories@email.com");
                    EverlastingMemories.setPassword("password");
                    EverlastingMemories.setDescription("Unforgettable wedding entertainment with DJs, live bands, magicians, and more. Create memories that last a lifetime");
                    EverlastingMemories.setWebsiteUrl("vendorURL.com");
                    EverlastingMemories.setInstagramUrl("This is the URL of instagram");
                    EverlastingMemories.setFacebookUrl("Facebook url");
                    EverlastingMemories.setWhatsappUrl("Whatsapp url");
                    EverlastingMemories.setCategory(CategoryEnum.ENTERTAINMENT);
                    vendorSessionBeanLocal.createVendor(EverlastingMemories);

                    Vendor WeddingPartyPros = new Vendor();
                    WeddingPartyPros.setUsername("WeddingPartyPros");
                    WeddingPartyPros.setEmail("WeddingPartyPros@email.com");
                    WeddingPartyPros.setPassword("password");
                    WeddingPartyPros.setDescription("Unforgettable wedding entertainment with DJs, live bands, magicians, and more. Create memories that last a lifetime");
                    WeddingPartyPros.setWebsiteUrl("vendorURL.com");
                    WeddingPartyPros.setInstagramUrl("This is the URL of instagram");
                    WeddingPartyPros.setFacebookUrl("Facebook url");
                    WeddingPartyPros.setWhatsappUrl("Whatsapp url");
                    WeddingPartyPros.setCategory(CategoryEnum.ENTERTAINMENT);
                    vendorSessionBeanLocal.createVendor(WeddingPartyPros);

                    Vendor vendor_food = new Vendor();
                    vendor_food.setUsername("FoodVendor");
                    vendor_food.setEmail("FoodVendor@email.com");
                    vendor_food.setPassword("password");
                    vendor_food.setDescription("Sample vendor description. I do these stuff");
                    vendor_food.setWebsiteUrl("vendorURL.com");
                    vendor_food.setInstagramUrl("This is the URL of instagram");
                    vendor_food.setFacebookUrl("Facebook url");
                    vendor_food.setWhatsappUrl("Whatsapp url");
                    vendor_food.setCategory(CategoryEnum.FOOD);

                    Request sampleRequest2 = new Request();
                    sampleRequest2.setIsAccepted(true);
                    sampleRequest2.setQuotationURL("www.fakeUrl.com for sample2");
                    sampleRequest2.setQuotedPrice(null);
                    sampleRequest2.setRequestDate(new Date());
                    sampleRequest2.setRequestDetails("Do some food stuff");
                    sampleRequest2.setVendor(vendor_food);
                    sampleRequest2.setWeddingProject(weddingProject1);
                    vendor_food.getRequests().add(sampleRequest2);

                    Transaction sampleTransaction2 = new Transaction();
                    sampleTransaction2.setIsPaid(true);
                    sampleTransaction2.setRequest(sampleRequest2);
                    sampleTransaction2.setTotalPrice(BigDecimal.TEN);
                    sampleTransaction2.setTransactionTime(new Date());
                    sampleRequest2.setTransaction(sampleTransaction2);

                    requestSessionBeanLocal.createRequest(sampleRequest2);
                    transactionSessionBeanLocal.createTransaction(sampleTransaction2);
                    vendorSessionBeanLocal.createVendor(vendor_food);

                    Vendor vendor_lighting = new Vendor();
                    vendor_lighting.setUsername("LightingVendor");
                    vendor_lighting.setEmail("LightingVendor@email.com");
                    vendor_lighting.setPassword("password");
                    vendor_lighting.setDescription("Sample vendor description. I do these stuff");
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
                    vendor_clothes.setWebsiteUrl("vendorURL.com");
                    vendor_clothes.setInstagramUrl("This is the URL of instagram");
                    vendor_clothes.setFacebookUrl("Facebook url");
                    vendor_clothes.setWhatsappUrl("Whatsapp url");
                    vendor_clothes.setCategory(CategoryEnum.CLOTHES);

                    Request sampleRequest3 = new Request();
                    sampleRequest3.setIsAccepted(true);
                    sampleRequest3.setQuotationURL("www.fakeUrl.com for sample2");
                    sampleRequest3.setQuotedPrice(BigDecimal.TEN);
                    sampleRequest3.setRequestDate(new Date());
                    sampleRequest3.setRequestDetails("Do some clothes stuff");
                    sampleRequest3.setVendor(vendor_clothes);
                    sampleRequest3.setWeddingProject(weddingProject1);
                    vendor_clothes.getRequests().add(sampleRequest3);

                    Transaction sampleTransaction3 = new Transaction();
                    sampleTransaction3.setIsPaid(true);
                    sampleTransaction3.setRequest(sampleRequest3);
                    sampleTransaction3.setTotalPrice(BigDecimal.TEN);
                    sampleTransaction3.setTransactionTime(new Date());
                    sampleRequest3.setTransaction(sampleTransaction3);

                    requestSessionBeanLocal.createRequest(sampleRequest3);
                    transactionSessionBeanLocal.createTransaction(sampleTransaction3);
                    vendorSessionBeanLocal.createVendor(vendor_clothes);

                    Vendor vendor_venue = new Vendor();
                    vendor_venue.setUsername("VenueVendor");
                    vendor_venue.setEmail("VenueVendor@email.com");
                    vendor_venue.setPassword("password");
                    vendor_venue.setDescription("Sample vendor description. I do these stuff");
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
//                    weddingProject1.setWeddingBudgetList(budget);
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

//                    WeddingChecklist weddingChecklist = new WeddingChecklist();
//                    weddingChecklist.setWeddingProject(weddingProject1);
//                    weddingProject1.setWeddingChecklist(weddingChecklist);
//                    em.persist(weddingChecklist);
//                    em.flush();

                    WeddingChecklist weddingProject1Checklist = weddingProject1.getWeddingChecklist();
                    WeddingParentTask parentTask = new WeddingParentTask();
                    parentTask.setTaskDescription("Sample Parent Task");
                    parentTask.setIsDone(false);
                    weddingChecklistSessionBean.createParentTask(parentTask, weddingProject1Checklist.getWeddingCheckListId());
                    em.persist(parentTask);
                    em.flush();
//                
                    WeddingSubtask subtask = new WeddingSubtask();
                    subtask.setSubtaskDescription("SubTask 1");
                    subtask.setIsDone(false);
                    weddingChecklistSessionBean.createSubtask(subtask, parentTask.getWeddingParentTaskId());
//                
                    subtask = new WeddingSubtask();
                    subtask.setSubtaskDescription("SubTask 2");
                    subtask.setIsDone(true);
                    weddingChecklistSessionBean.createSubtask(subtask, parentTask.getWeddingParentTaskId());

                    em.flush();
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
