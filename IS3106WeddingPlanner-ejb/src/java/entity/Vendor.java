/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumeration.CategoryEnum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

/**
 *
 * @author joelt
 */
@Entity
public class Vendor extends UserEntity implements Serializable {

    private static long serialVersionUID = 1L;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String websiteUrl;
    @Column(nullable = false)
    private String instagramUrl;
    @Column(nullable = false)
    private String facebookUrl;
    @Column(nullable = false)
    private String whatsappUrl; 
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryEnum category; 
    
    @OneToMany(mappedBy="vendor")
    private List<Request> requests;
            

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    public Vendor() {
        super();
        this.requests = new ArrayList<Request>();
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the vendorId fields are not set
        if (!(object instanceof Vendor)) {
            return false;
        }
        Vendor other = (Vendor) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Vendor[ id=" + userId + " ]";
    }

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the websiteUrl
     */
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    /**
     * @param websiteUrl the websiteUrl to set
     */
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    /**
     * @return the instagramUrl
     */
    public String getInstagramUrl() {
        return instagramUrl;
    }

    /**
     * @param instagramUrl the instagramUrl to set
     */
    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    /**
     * @return the facebookUrl
     */
    public String getFacebookUrl() {
        return facebookUrl;
    }

    /**
     * @param facebookUrl the facebookUrl to set
     */
    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    /**
     * @return the whatsappUrl
     */
    public String getWhatsappUrl() {
        return whatsappUrl;
    }

    /**
     * @param whatsappUrl the whatsappUrl to set
     */
    public void setWhatsappUrl(String whatsappUrl) {
        this.whatsappUrl = whatsappUrl;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
    
}
