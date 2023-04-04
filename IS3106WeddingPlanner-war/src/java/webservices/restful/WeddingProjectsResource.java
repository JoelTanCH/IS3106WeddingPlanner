/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.WeddingProject;
import error.WeddingOrganiserNotFoundException;
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
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.WeddingProjectSessionBeanLocal;

/**
 * REST Web Service
 *
 * @author wangp
 */
@Path("wedding-projects")
@RequestScoped
public class WeddingProjectsResource {

    @EJB
    WeddingProjectSessionBeanLocal weddingProjectSessionBeanLocal;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WeddingProjectResource
     */
    public WeddingProjectsResource() {
    }

    @GET
    @Path("/{wedding-organiser-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWeddingOrganisers(@PathParam("wedding-organiser-id") Long organiserId) {
        try {
            List<WeddingProject> projects = weddingProjectSessionBeanLocal.getAllWeddingProjectbyOrganiser(organiserId);
            return Response.status(200).entity(projects).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Wedding Project Not Found")
                    .add("exceptionMessage", e.getMessage())
                    .build();
            return Response.status(500).entity(exception).build();
        }
    }
    
    @POST
    @Path("/{wedding-organiser-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createWeddingProject(@PathParam("wedding-organiser-id") Long organiserId, WeddingProject w) {
            weddingProjectSessionBeanLocal.createWeddingProject(organiserId, w);
            return Response.status(200).build();
    }
    
    @PUT
    @Path("/{wedding-checklist-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWeddingProject(@PathParam("wedding-organiser-id") Long organiserId, WeddingProject w) {
        try {
            weddingProjectSessionBeanLocal.updateWeddingProject(w);
            return Response.status(200).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Problem with the wedding project")
                    .add("exceptionMessage", e.getMessage())
                    .build();
            return Response.status(500).entity(exception).build();
        }
    }

//    /**
//     * Retrieves representation of an instance of webservices.restful.WeddingProjectsResource
//     * @return an instance of java.lang.String
//     */
//    @GET
//    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
//    public String getJson() {
//        //TODO return proper representation object
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * PUT method for updating or creating an instance of WeddingProjectsResource
//     * @param content representation for the resource
//     */
//    @PUT
//    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
//    public void putJson(String content) {
//    }
}
