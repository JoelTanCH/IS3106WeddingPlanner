/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.WeddingOrganiser;
import error.WeddingOrganiserNotFoundException;
import error.WeddingProjectNotFoundException;
import java.util.List;
import javax.ejb.Local;
import util.exception.InvalidDeleteException;

/**
 *
 * @author PERSONAL
 */
@Local
public interface WeddingOrganiserSessionBeanLocal {

    public void deleteWeddingOrganiser(Long wId) throws WeddingOrganiserNotFoundException, WeddingProjectNotFoundException, InvalidDeleteException;

    public void updateWeddingOrganiser(WeddingOrganiser w) throws WeddingOrganiserNotFoundException;

    public WeddingOrganiser getWeddingOrganiser(Long wId) throws WeddingOrganiserNotFoundException;

    public void createWeddingOrganiser(WeddingOrganiser w);

    public List<WeddingOrganiser> getAllWeddingOrganisers();
    
}
