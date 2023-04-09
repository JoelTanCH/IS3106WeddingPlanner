/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Vendor;
import entity.WeddingOrganiser;
import error.WeddingOrganiserNotFoundException;
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
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.WeddingOrganiserSessionBeanLocal;

/**
 * REST Web Service
 *
 * @author wangp
 */
@Path("wedding-organisers")
@RequestScoped
public class WeddingOrganisersResource {

    @EJB
    WeddingOrganiserSessionBeanLocal weddingOrganiserSessionBeanLocal;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of VendorsResource
     */
    public WeddingOrganisersResource() {
    }
    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<WeddingOrganiser> getAllWeddingOrganisers() {
        List<WeddingOrganiser> wOrganisers = weddingOrganiserSessionBeanLocal.getAllWeddingOrganisers();

        for (WeddingOrganiser w : wOrganisers) {
            w.setWeddingProjects(null);
        }

        return wOrganisers;
    }

    @GET
    @Path("/{wedding-organiser-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWeddingOrganiser(@PathParam("wedding-organiser-id") Long wId) {
        try {
            WeddingOrganiser w = weddingOrganiserSessionBeanLocal.getWeddingOrganiser(wId);
            w.setWeddingProjects(null);
            return Response.status(200).entity(w).type(MediaType.APPLICATION_JSON).build();
        } catch (WeddingOrganiserNotFoundException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(500).entity(exception).build();
        }
    }

    @PUT
    @Path("/{wedding-organiser-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWeddingOrganiser(@PathParam("wedding-organiser-id") Long wId, WeddingOrganiser w) {
        try {
            w.setUserId(wId);
            weddingOrganiserSessionBeanLocal.updateWeddingOrganiser(w);
            return Response.status(200).build();
        } catch (WeddingOrganiserNotFoundException e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "no idea what happened, update went wrong ")
                    .add("exceptionMessage", e.getMessage())
                    .build();
            return Response.status(404).entity(exception).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createWeddingOrganiser(WeddingOrganiser w) {
        try {
            weddingOrganiserSessionBeanLocal.createWeddingOrganiser(w);
            // should it return a json object with a "success" message? 
            return Response.status(200).build();
        } catch (Exception e) {
            // response status 500 is internal server error
            JsonObject exception = Json.createObjectBuilder().add("error", "Probably wedding organiser with same attributes already exists")
                    .add("exceptionMessage", e.getMessage())
                    .build();
            return Response.status(500).entity(exception).build();
        }
    }

//    @GET
//    @Path("/query")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response searchVendors(@QueryParam("username") String username,
//            @QueryParam("password") String password,
//            @QueryParam("email") String email) {
//        
//
//        if (email == null && username != null && password != null) {
//            List<Admin> gotAdminsByUsernamePassword = adminSessionBeanLocal.getAdminsByUsernamePassword(username, password);
//
//            // curly braces in the line below is an anonymous inner class. Since GenericEntity is an abstract class,
//            // we need to declare sth that extends from it, and cannot instantiate it by itself.
//            // the syntax below creates an empty class extending GenericEntity, with the same effect like` A extends GenericEntity {} `
//            GenericEntity<List<Admin>> entityToReturn = new GenericEntity<List<Admin>>(gotAdminsByUsernamePassword) {};
//
//            // response status 200 means "OK"
//            return Response.status(200).entity(entityToReturn).build();
//        } else if (password == null && username != null && email != null) {
//            List<Admin> searchedAdminsByUsernameEmail = adminSessionBeanLocal.searchAdminsByUsernameEmail(username, email);
//
//            GenericEntity<List<Admin>> entity = new GenericEntity<List<Admin>>(searchedAdminsByUsernameEmail) {
//            };
//
//            return Response.status(200).entity(entity).build();
//
//        } else {
//            JsonObject exception = Json.createObjectBuilder().add("error", "No query or invalid query")
//                    .build();
//
//            // 400 is error; invalid request
//            return Response.status(400).entity(exception).build();
//
//        }
//
//    }
//
}
