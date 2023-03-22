/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.GuestTable;
import javax.ejb.Local;
import util.exception.InvalidAssociationException;

/**
 *
 * @author leomi
 */
@Local
public interface GuestTableSessionBeanLocal {

    public void deleteGuestTable(Long guestTableId);

    public void addGuestToTable(Long guestId, Long guestTableId) throws InvalidAssociationException;

    public void createGuestTable(GuestTable guestTable, Long weddingProjectId);

    public void removeGuestFromTable(Long guestId, Long guestTableId) throws InvalidAssociationException;
    
}
