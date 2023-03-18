/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Admin;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;
import session.AdminSessionBeanLocal;

/**
 * REST Web Service
 *
 * @author wangp
 */
@Path("admins")
@RequestScoped
public class AdminsResource {

    @EJB
    AdminSessionBeanLocal adminSessionBeanLocal;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AdminsResource
     */
    public AdminsResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Admin> getAllAdmins() {
        return adminSessionBeanLocal.searchAdminsByUsernameEmail("", "");
        // pass in empty strings for the search, so it basically returns all admins
        
    }

    /**
     * Retrieves representation of an instance of
     * webservices.restful.AdminsResource
     *
     * @return an instance of java.lang.String
     */
    /**
     * PUT method for updating or creating an instance of AdminsResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

}
