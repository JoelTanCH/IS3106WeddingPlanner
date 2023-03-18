/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Vendor;
import enumeration.CategoryEnum;
import error.InvalidVendorCategory;
import error.VendorNameNotFoundException;
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
    public List<Vendor> getVendorByCategory(String vendorCategorySelection) throws InvalidVendorCategory { //ENSURE STRING GIVEN SAME AS ENUMERATION STRING IN FROTNEND
        List<Vendor> allVendors = getAllVendors();
        List<Vendor> vendorsInCategory = new ArrayList<>();
        if (vendorCategorySelection == "Entertainment") {
            for (Vendor vendor : allVendors) {
                if (vendor.getCategory() == CategoryEnum.ENTERTAINMENT) {
                    vendorsInCategory.add(vendor);
                }
            }
        } else if (vendorCategorySelection == "Food") {
            for (Vendor vendor : allVendors) {
                if (vendor.getCategory() == CategoryEnum.FOOD) {
                    vendorsInCategory.add(vendor);
                }
            }
        } else if (vendorCategorySelection == "Lighting") {
            for (Vendor vendor : allVendors) {
                if (vendor.getCategory() == CategoryEnum.LIGHTING) {
                    vendorsInCategory.add(vendor);
                }
            }
        } else if (vendorCategorySelection == "Decoration") {
            for (Vendor vendor : allVendors) {
                if (vendor.getCategory() == CategoryEnum.DECORATION) {
                    vendorsInCategory.add(vendor);
                }
            }
        } else if (vendorCategorySelection == "Dresses&Suits") {

            for (Vendor vendor : allVendors) {
                if (vendor.getCategory() == CategoryEnum.DRESSES_SUITS) {
                    vendorsInCategory.add(vendor);
                }
            }
        } else if (vendorCategorySelection == "Entertainment") {

            for (Vendor vendor : allVendors) {
                if (vendor.getCategory() == CategoryEnum.ENTERTAINMENT) {
                    vendorsInCategory.add(vendor);
                }
            }
        } else {
            throw new InvalidVendorCategory("Invalid Vendor Category: " + vendorCategorySelection);
        }
        return vendorsInCategory;
    }
    
    @Override //should names of vendors be unique? 
    public Vendor getVendorByVendorName(String vendorName) throws VendorNameNotFoundException{
        try{
            Query query = em.createQuery("SELECT v FROM Vendor v WHERE v.username =:vendorName");
            query.setParameter("vendorName", vendorName);
            return (Vendor)query.getSingleResult();
        }catch(Exception e){
            throw new VendorNameNotFoundException(e.getMessage());
        }
    }

}
