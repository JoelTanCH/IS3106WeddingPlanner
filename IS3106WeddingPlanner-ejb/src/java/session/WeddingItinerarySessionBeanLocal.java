/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.WeddingItinerary;
import javax.ejb.Local;

/**
 *
 * @author Josephine
 */
@Local
public interface WeddingItinerarySessionBeanLocal {

    public void createNewItinerary(WeddingItinerary i);

    public void updateItinerary(WeddingItinerary i);

    public void deleteItinerary(Long itineraryId);
    
}
