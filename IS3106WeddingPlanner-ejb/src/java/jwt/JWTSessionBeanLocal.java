/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jwt;

import io.jsonwebtoken.JwtException;
import javax.ejb.Local;

/**
 *
 * @author leomi
 */
@Local
public interface JWTSessionBeanLocal {

    public String generateToken(String role);
    public String verifyToken(String token) throws JwtException;
    
}
