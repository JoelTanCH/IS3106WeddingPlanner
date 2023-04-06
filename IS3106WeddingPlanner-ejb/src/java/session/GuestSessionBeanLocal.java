/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Guest;
import java.util.List;
import java.util.Optional;
import javax.ejb.Local;
import util.exception.InvalidAssociationException;
import util.exception.InvalidDeleteException;
import util.exception.InvalidGetException;
import util.exception.InvalidUpdateException;

/**
 *
 * @author leomi
 */
@Local
public interface GuestSessionBeanLocal {

    public Long createGuest(Guest guest, Long weddingProjectId) throws InvalidAssociationException;

    public void deleteGuest(Long guestId) throws InvalidDeleteException;

    public void updateGuest(Guest guest) throws InvalidUpdateException;

    public List<Guest> getGuests(Long weddingId) throws InvalidGetException;

    public void updateGuestsRSVP(List<Guest> guests) throws InvalidUpdateException;

    public void updateGuestRSVP(String email, String rsvpStatus, Long weddingId) throws Throwable;

    public Optional<Guest> getGuest(String email, Long weddingId);
    
}
