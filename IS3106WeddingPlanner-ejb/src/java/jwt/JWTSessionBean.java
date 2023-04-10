/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author leomi
 */
@Stateless
public class JWTSessionBean implements JWTSessionBeanLocal {
    @EJB
    private KeyHolderLocal keyHolder;
    @Override
    public String generateToken(String role) { 
            Date date = new Date();
            date.setDate(date.getDate() + 1);
            return Jwts.builder().setSubject(role).setExpiration(date).signWith(keyHolder.getKey()).compact(); 
    }
    
    @Override
    public String verifyToken(String token) throws JwtException {
        try {
            return Jwts.parserBuilder().setSigningKey(keyHolder.getKey()).build().parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException e) {
            throw e;
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

}
