/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Josephine
 */
@Entity
public class WeddingProject implements Serializable {

    /**
     * @return the completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * @param completed the completed to set
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * @return the weddingBudgetList
     */
    public WeddingBudgetList getWeddingBudgetList() {
        return weddingBudgetList;
    }

    /**
     * @param weddingBudgetList the weddingBudgetList to set
     */
    public void setWeddingBudgetList(WeddingBudgetList weddingBudgetList) {
        this.weddingBudgetList = weddingBudgetList;
    }

    /**
     * @return the weddingChecklist
     */
    public WeddingChecklist getWeddingChecklist() {
        return weddingChecklist;
    }

    /**
     * @param weddingChecklist the weddingChecklist to set
     */
    public void setWeddingChecklist(WeddingChecklist weddingChecklist) {
        this.weddingChecklist = weddingChecklist;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weddingProjectId;
    
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private boolean completed;

    @ManyToOne
    private WeddingOrganiser weddingOrganiser;

    @OneToMany(mappedBy = "weddingProject")
    private List<Request> requests;

    @OneToMany(mappedBy = "weddingProject") // unidirectional
    private List<Guest> guests;

    @OneToOne
    private WeddingBudgetList weddingBudgetList;

    @OneToOne
    private WeddingChecklist weddingChecklist;

    @OneToMany(mappedBy = "weddingProject")
    private List<WeddingItinerary> weddingItineraries;

    @OneToMany(mappedBy = "weddingProject")
    private List<GuestTable> tables;

    @Temporal(TemporalType.DATE)
    private Date weddingDate;
    @Temporal(TemporalType.TIME)
    private Date weddingStartTime;
    @Temporal(TemporalType.TIME)
    private Date weddingEndTime;

    private String venue;
    
    
    public WeddingProject() {
        this.requests = new ArrayList<>();
        this.guests = new ArrayList<>();
        this.weddingItineraries = new ArrayList<>();
        this.tables = new ArrayList<>();
    }

    /**
     * @return the weddingOrganiser
     */
    public WeddingOrganiser getWeddingOrganiser() {
        return weddingOrganiser;
    }

    /**
     * @param weddingOrganiser the weddingOrganiser to set
     */
    public void setWeddingOrganiser(WeddingOrganiser weddingOrganiser) {
        this.weddingOrganiser = weddingOrganiser;
    }

    /**
     * @return the requests
     */
    public List<Request> getRequests() {
        return requests;
    }

    /**
     * @param requests the requests to set
     */
    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    /**
     * @return the guests
     */
    public List<Guest> getGuests() {
        return guests;
    }

    /**
     * @param guests the guests to set
     */
    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    /**
     * @return the weddingItineraries
     */
    public List<WeddingItinerary> getWeddingItineraries() {
        return weddingItineraries;
    }

    /**
     * @param weddingItineraries the weddingItineraries to set
     */
    public void setWeddingItineraries(List<WeddingItinerary> weddingItineraries) {
        this.weddingItineraries = weddingItineraries;
    }

    /**
     * @return the tables
     */
    public List<GuestTable> getTables() {
        return tables;
    }

    /**
     * @param tables the tables to set
     */
    public void setTables(List<GuestTable> tables) {
        this.tables = tables;
    }
    
    public Date getWeddingDate() {
        return weddingDate;
    }

    public void setWeddingDate(Date weddingDate) {
        this.weddingDate = weddingDate;
    }

    public Date getWeddingStartTime() {
        return weddingStartTime;
    }

    public void setWeddingStartTime(Date weddingStartTime) {
        this.weddingStartTime = weddingStartTime;
    }

    public Date getWeddingEndTime() {
        return weddingEndTime;
    }

    public void setWeddingEndTime(Date weddingEndTime) {
        this.weddingEndTime = weddingEndTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
        
    
    public Long getWeddingProjectId() {
        return weddingProjectId;
    }

    public void setWeddingProjectId(Long weddingProjectId) {
        this.weddingProjectId = weddingProjectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (weddingProjectId != null ? weddingProjectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the weddingProjectId fields are not set
        if (!(object instanceof WeddingProject)) {
            return false;
        }
        WeddingProject other = (WeddingProject) object;
        if ((this.weddingProjectId == null && other.weddingProjectId != null) || (this.weddingProjectId != null && !this.weddingProjectId.equals(other.weddingProjectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.WeddingProject[ id=" + weddingProjectId + " ]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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

}
