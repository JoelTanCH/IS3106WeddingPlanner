/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Admin;
import entity.Vendor;
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
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.VendorSessionBeanLocal;

/**
 * REST Web Service
 *
 * @author wangp
 */
@Path("vendors")
@RequestScoped
public class VendorsResource {

    @EJB
    VendorSessionBeanLocal vendorSessionBeanLocal;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of VendorsResource
     */
    public VendorsResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //public List<Vendor> getAllVendors() {
    public Response getAllVendors(){
        List<Vendor> allVendors = vendorSessionBeanLocal.getAllVendors();
        for(Vendor vendor : allVendors){
            vendor.setRequests(null);
        }
        return Response.status(200).entity(allVendors).type(MediaType.APPLICATION_JSON).build();
        //return allVendors;
    }
    
    @GET
    @Path("{vendorName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVendor(@PathParam("vendorName") String vendorName) {
        try {
            Vendor vendor = vendorSessionBeanLocal.getVendorByVendorName(vendorName);
            vendor.setRequests(null);
            return Response.status(200).entity(vendor).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            System.out.println("Error === "+ e.getMessage());
            //Template to throw error. Can change
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @GET
    @Path("allCategories/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVendorsInCategory(@PathParam("category") String category) {
        try{
            List<Vendor> vendorsInCategory = vendorSessionBeanLocal.getVendorByCategory(category);
            for(Vendor vendor : vendorsInCategory){
                vendor.setRequests(null);
            }
            return Response.status(200).entity(vendorsInCategory).type(MediaType.APPLICATION_JSON).build();
        }catch (Exception e) {
            System.out.println("Error === "+ e.getMessage());

            //Template to throw error. Can change
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    
    

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateVendor(Vendor v) {
        try {
            vendorSessionBeanLocal.updateVendor(v);
            return Response.status(200).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "no idea what happened, update went wrong ")
                    .add("exceptionMessage", e.getMessage())
                    .build();
            return Response.status(500).entity(exception).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createVendor(Vendor v) {
        try {
            vendorSessionBeanLocal.createVendor(v);
            // should it return a json object with a "success" message? 
            return Response.status(200).build();
        } catch (Exception e) {
            // response status 500 is internal server error
            JsonObject exception = Json.createObjectBuilder().add("error", "Probably vendor with same attributes already exists")
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
