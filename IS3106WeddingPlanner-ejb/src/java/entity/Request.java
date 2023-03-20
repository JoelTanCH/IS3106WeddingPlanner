/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author joelt
 */
@Entity
public class Request implements Serializable {

    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    @Column(nullable = false)
    private Boolean isAccepted;
    @Column(nullable = false)
    private String quotationURL;
    @Column(nullable = false)
    private BigDecimal quotedPrice;
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;
    @Column(nullable = false)
    private String requestDetails;

    @OneToOne(optional = true, mappedBy="request")
    private WeddingBudgetItem weddingBudgetItem;

    
    //Made vendor and wedding project optional for testing. In deployment should be compulsory
    @ManyToOne(optional = true)
    private Vendor vendor;

    @ManyToOne(optional = true)
    private WeddingProject weddingProject;
        
    @OneToOne(mappedBy = "request")
    private Transaction transaction;



    public Request() {
    }

    
    
    /**
     * @return the vendor
     */
    public Vendor getVendor() {
        return vendor;
    }

    /**
     * @param vendor the vendor to set
     */
    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    /**
     * @return the transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * @param transaction the transaction to set
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

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
    
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public WeddingBudgetItem getWeddingBudgetItem() {
        return weddingBudgetItem;
    }

    public void setWeddingBudgetItem(WeddingBudgetItem weddingBudgetItem) {
        this.weddingBudgetItem = weddingBudgetItem;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getRequestId() != null ? getRequestId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the requestId fields are not set
        if (!(object instanceof Request)) {
            return false;
        }
        Request other = (Request) object;
        if ((this.getRequestId() == null && other.getRequestId() != null) || (this.getRequestId() != null && !this.requestId.equals(other.requestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Request[ id=" + getRequestId() + " ]";
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
     * @return the isAccepted
     */
    public Boolean getIsAccepted() {
        return isAccepted;
    }

    /**
     * @param isAccepted the isAccepted to set
     */
    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    /**
     * @return the quotationURL
     */
    public String getQuotationURL() {
        return quotationURL;
    }

    /**
     * @param quotationURL the quotationURL to set
     */
    public void setQuotationURL(String quotationURL) {
        this.quotationURL = quotationURL;
    }

    /**
     * @return the quotedPrice
     */
    public BigDecimal getQuotedPrice() {
        return quotedPrice;
    }

    /**
     * @param quotedPrice the quotedPrice to set
     */
    public void setQuotedPrice(BigDecimal quotedPrice) {
        this.quotedPrice = quotedPrice;
    }

    /**
     * @return the requestDate
     */
    public Date getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate the requestDate to set
     */
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * @return the requestDetails
     */
    public String getRequestDetails() {
        return requestDetails;
    }

    /**
     * @param requestDetails the requestDetails to set
     */
    public void setRequestDetails(String requestDetails) {
        this.requestDetails = requestDetails;
    }

}
