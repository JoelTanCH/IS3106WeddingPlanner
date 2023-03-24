/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.WeddingProject;
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
public interface WeddingProjectSessionBeanLocal {

    public void createWeddingProject(Long organiserId, WeddingProject w);

    public WeddingProject getWeddingProject(Long wId) throws WeddingProjectNotFoundException;

    public void updateWeddingProject(WeddingProject w) throws WeddingProjectNotFoundException;

    public List<WeddingProject> getAllWeddingProjectbyOrganiser(Long wId) throws WeddingOrganiserNotFoundException;

    public List<WeddingProject> getAllWeddingProject();
    
    public void deleteWeddingProject(Long wId) throws WeddingProjectNotFoundException, InvalidDeleteException;

    public List<WeddingProject> searchWeddingProjectbyName(String name) throws WeddingProjectNotFoundException;
    
}
