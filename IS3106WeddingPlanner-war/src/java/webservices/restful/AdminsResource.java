/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Admin;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAdmins(@QueryParam("username") String username,
            @QueryParam("password") String password,
            @QueryParam("email") String email) {
        
        if (email == null && username != null && password != null) {
            List<Admin> gotAdminsByUsernamePassword = adminSessionBeanLocal.getAdminsByUsernamePassword(username, password);
            
            // curly braces in the line below is an anonymous inner class. Since GenericEntity is an abstract class,
            // we need to declare sth that extends from it, and cannot instantiate it by itself.
            // the syntax below creates an empty class extending GenericEntity, with the same effect like` A extends GenericEntity {} `
            GenericEntity<List<Admin>> entityToReturn = new GenericEntity<List<Admin>>(gotAdminsByUsernamePassword) {};
            
            // response status 200 means "OK"
            return Response.status(200).entity(entityToReturn).build();
        } else if (password == null && username != null && email != null) {
            List<Admin> searchedAdminsByUsernameEmail = adminSessionBeanLocal.searchAdminsByUsernameEmail(username, email);
            
            GenericEntity<List<Admin>> entity = new GenericEntity<List<Admin>>(searchedAdminsByUsernameEmail) {};
            
            return Response.status(200).entity(entity).build();
            
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "No query or invalid query")
                    .build();
            
            // 400 is error; invalid request
            return Response.status(400).entity(exception).build();
            
            
        }
        
        
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAdmin(Admin admin) {
        try {
            adminSessionBeanLocal.createAdmin(admin);
            return Response.status(200).build();
        } catch (Exception e) {
            // response status 500 is internal server error
            return Response.status(500).build();
        }
    }
    
    @DELETE
    @Path("/{admin-id}")
    public void removeAdmin(@PathParam("admin-id") Long adminId) {
        adminSessionBeanLocal.deleteAdmin(adminId);
    }
    

}
