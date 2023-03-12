/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Guest;
import javax.ejb.Local;

/**
 *
 * @author leomi
 */
@Local
public interface GuestSessionBeanLocal {

    public void createGuest(Guest guest, Long weddingProjectId);

    public void deleteGuest(Long guestId);
    
}
