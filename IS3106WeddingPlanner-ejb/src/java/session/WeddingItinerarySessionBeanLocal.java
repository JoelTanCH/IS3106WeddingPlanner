/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.WeddingItinerary;
import java.util.List;
import javax.ejb.Local;
import util.exception.InvalidAssociationException;

/**
 *
 * @author Josephine
 */
@Local
public interface WeddingItinerarySessionBeanLocal {
    
    public Long createNewItinerary(WeddingItinerary i, Long weddingProjectId) throws InvalidAssociationException;

//    public void createNewItinerary(WeddingItinerary i);

    public void updateItinerary(WeddingItinerary i);

    public void deleteItinerary(Long itineraryId);

    public List<WeddingItinerary> getAllItineraries();

    public WeddingItinerary getItinerary(Long weddingItineraryId);

    public List<WeddingItinerary> getWeddingItinerariesByWeddingProject(Long weddingId);

    public List<WeddingItinerary> getWeddingItinerary(Long weddingId);
    
}
