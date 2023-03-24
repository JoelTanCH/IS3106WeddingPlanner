/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jwt;

import java.security.Key;
import javax.ejb.Local;

/**
 *
 * @author leomi
 */
@Local
public interface KeyHolderLocal {

    public void setKey(Key key);

    public Key getKey();
    
}
