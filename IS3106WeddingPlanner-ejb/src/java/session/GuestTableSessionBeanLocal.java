/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.GuestTable;
import java.util.List;
import javax.ejb.Local;
import util.exception.InvalidAssociationException;
import util.exception.InvalidDeleteException;
import util.exception.InvalidGetException;

/**
 *
 * @author leomi
 */
@Local
public interface GuestTableSessionBeanLocal {

    public void deleteGuestTable(Long guestTableId) throws InvalidDeleteException;

    public void addGuestToTable(Long guestId, Long guestTableId) throws InvalidAssociationException;

    public Long createGuestTable(GuestTable guestTable, Long weddingProjectId) throws InvalidAssociationException;

    public void removeGuestFromTable(Long guestId, Long guestTableId) throws InvalidAssociationException;

    public List<GuestTable> getGuestTables(Long weddingId) throws InvalidGetException;

    public void updateGuestTables(List<GuestTable> tables, Long weddingId) throws InvalidAssociationException;
    
}
