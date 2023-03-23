/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.WeddingOrganiser;
import error.WeddingOrganiserNotFoundException;
import javax.ejb.Local;

/**
 *
 * @author PERSONAL
 */
@Local
public interface WeddingOrganiserSessionBeanLocal {

    public void updateWeddingOrganiser(WeddingOrganiser w) throws WeddingOrganiserNotFoundException;

    public void createWeddingOrganiser(WeddingOrganiser w);

    public WeddingOrganiser getWeddingOrganiser(Long wId) throws WeddingOrganiserNotFoundException;

    public void deleteWeddingOrganiser(Long wId) throws WeddingOrganiserNotFoundException;
    
}
