/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Request;
import entity.Vendor;
import enumeration.CategoryEnum;
import error.InvalidVendorCategory;
import error.VendorNameNotFoundException;
import error.VendorNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author joelt
 */
@Stateless
public class VendorSessionBean implements VendorSessionBeanLocal {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;

    @Override
    public List<Vendor> getAllVendors() {
        Query query = em.createQuery("SELECT v FROM Vendor v");
        return query.getResultList();
    }
    @Override
    public Vendor getVendor(Long vId) throws VendorNotFoundException {
        Vendor v = em.find(Vendor.class, vId);
        if (v != null) {
            return v;
        } else {
            throw new VendorNotFoundException("Vendor Not Found");
        }
    }

    @Override
    public Vendor getVendorByRequestId(Long requestId) {
        Request request = em.find(Request.class, requestId);
        Vendor vendor = request.getVendor();
        return vendor;
    }

    // im using List instead of the single one because idw to throw an exception
    @Override
    public List<Vendor> getVendorsByUsernamePassword(String username, String password) {
        Query q;
        q = em.createQuery("SELECT v FROM Vendor v WHERE v.username LIKE :username AND v.password LIKE :password");
        q.setParameter("username", username);
        q.setParameter("password", password);
        return q.getResultList();
    }

    @Override
    public List<Vendor> getVendorByCategory(String selection) throws InvalidVendorCategory { //ENSURE STRING GIVEN SAME AS ENUMERATION STRING IN FROTNEND
        List<Vendor> allVendors = getAllVendors();
        List<Vendor> vendorsInCategory = new ArrayList<>();
        String vendorCategorySelection = selection.toLowerCase();
        System.out.println("vendorCategorySelection = " + vendorCategorySelection);
        if (vendorCategorySelection.equals("entertainment")) { //could use .contains here 
            for (Vendor vendor : allVendors) {
                if (vendor.getCategory() == CategoryEnum.ENTERTAINMENT) {
                    vendorsInCategory.add(vendor);
                }
            }
        } else if (vendorCategorySelection.equals("food")) {
            for (Vendor vendor : allVendors) {
                if (vendor.getCategory() == CategoryEnum.FOOD) {
                    vendorsInCategory.add(vendor);
                }
            }
        } else if (vendorCategorySelection.equals("lighting")) {
            for (Vendor vendor : allVendors) {
                if (vendor.getCategory() == CategoryEnum.LIGHTING) {
                    vendorsInCategory.add(vendor);
                }
            }
        } else if (vendorCategorySelection.equals("decoration")) {
            for (Vendor vendor : allVendors) {
                if (vendor.getCategory() == CategoryEnum.DECORATION) {
                    vendorsInCategory.add(vendor);
                }
            }
        } else if (vendorCategorySelection.equals("clothes")) {

            for (Vendor vendor : allVendors) {
                if (vendor.getCategory() == CategoryEnum.CLOTHES) {
                    vendorsInCategory.add(vendor);
                }
            }
        } else if (vendorCategorySelection.equals("entertainment")) {

            for (Vendor vendor : allVendors) {
                if (vendor.getCategory() == CategoryEnum.ENTERTAINMENT) {
                    vendorsInCategory.add(vendor);
                }
            }
        }else if (vendorCategorySelection.equals("venue")) {

            for (Vendor vendor : allVendors) {
                if (vendor.getCategory() == CategoryEnum.VENUE) {
                    vendorsInCategory.add(vendor);
                }
            }
        } else {
            throw new InvalidVendorCategory("Invalid Vendor Category: " + vendorCategorySelection);
        }
        System.out.println("vendors in category = " + vendorsInCategory.get(0));
        return vendorsInCategory;
    }

    @Override //should names of vendors be unique? 
    public Vendor getVendorByVendorName(String vendorName) throws VendorNameNotFoundException {
        try {
            Query query = em.createQuery("SELECT v FROM Vendor v WHERE v.username =:vendorName");
            query.setParameter("vendorName", vendorName);
            return (Vendor) query.getSingleResult();
        } catch (Exception e) {
            throw new VendorNameNotFoundException(e.getMessage());
        }
    }
    
    @Override
    public Vendor getVendorById(Long vId) throws VendorNotFoundException {
         Vendor v = em.find(Vendor.class, vId);

        if (v != null) {
            return v;
        } else {
            throw new VendorNotFoundException("Vendor Not Found");
        }
    }

    @Override
    public Long createVendor(Vendor vendor) {
        em.persist(vendor);
        em.flush();
        return vendor.getUserId();
    }

    @Override
    public void updateVendor(Vendor v) throws VendorNotFoundException {
        Vendor vOld = getVendorById(v.getUserId());

        vOld.setUsername(v.getUsername());
        vOld.setEmail(v.getEmail());
        vOld.setPassword(v.getPassword());
        vOld.setIsBanned(v.isIsBanned());
        vOld.setCategory(v.getCategory());
        vOld.setDescription(v.getDescription());
        vOld.setFacebookUrl(v.getFacebookUrl());
        vOld.setInstagramUrl(v.getInstagramUrl());
        vOld.setWebsiteUrl(v.getWebsiteUrl());
        vOld.setWhatsappUrl(v.getWhatsappUrl());
    }
}
