/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Josephine
 */
@Entity
public class WeddingItinerary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weddingItineraryId;

    @Column(nullable=false)
    private String eventName;
    @Temporal(TemporalType.DATE)
    private Date eventDate;
    @Temporal(TemporalType.TIME)
    private Date eventStartTime;
    @Temporal(TemporalType.TIME)
    private Date eventEndTime;
    
    @ManyToOne
    private WeddingProject weddingProject;
    
        /**
     * @return the weddingProject
     */
    public WeddingProject getWeddingProject() {
        return weddingProject;
    }

    /**
     * @param weddingProject the weddingProject to set
     */
    public void setWeddingProject(WeddingProject weddingProject) {
        this.weddingProject = weddingProject;
    }
    
    /**
     * @return the eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * @param eventName the eventName to set
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * @return the eventDate
     */
    public Date getEventDate() {
        return eventDate;
    }

    /**
     * @param eventDate the eventDate to set
     */
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * @return the eventStartTime
     */
    public Date getEventStartTime() {
        return eventStartTime;
    }

    /**
     * @param eventStartTime the eventStartTime to set
     */
    public void setEventStartTime(Date eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    /**
     * @return the eventEndTime
     */
    public Date getEventEndTime() {
        return eventEndTime;
    }

    /**
     * @param eventEndTime the eventEndTime to set
     */
    public void setEventEndTime(Date eventEndTime) {
        this.eventEndTime = eventEndTime;
    }
    
    public Long getWeddingItineraryId() {
        return weddingItineraryId;
    }

    public void setWeddingItineraryId(Long weddingItineraryId) {
        this.weddingItineraryId = weddingItineraryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (weddingItineraryId != null ? weddingItineraryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the weddingItineraryId fields are not set
        if (!(object instanceof WeddingItinerary)) {
            return false;
        }
        WeddingItinerary other = (WeddingItinerary) object;
        if ((this.weddingItineraryId == null && other.weddingItineraryId != null) || (this.weddingItineraryId != null && !this.weddingItineraryId.equals(other.weddingItineraryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.WeddingItinerary[ id=" + weddingItineraryId + " ]";
    }
    
}
