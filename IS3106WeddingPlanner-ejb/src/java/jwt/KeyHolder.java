/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jwt;

import java.security.Key;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

/**
 *
 * @author leomi
 */
@Singleton
public class KeyHolder implements KeyHolderLocal {
    private Key key;
    public KeyHolder() {
        
    }
    @Override
    @Lock(LockType.WRITE)
    public void setKey(Key key) {
        this.key = key;
    }
    @Override
    @Lock(LockType.READ)   
    public Key getKey() {
        return this.key;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
