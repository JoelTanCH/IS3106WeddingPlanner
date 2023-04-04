/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Admin;
import entity.WeddingProject;
import error.WeddingProjectNotFoundException;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
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

    @Context
    private UriInfo context;

    @EJB
    private WeddingProjectSessionBeanLocal weddingProjectSessionBeanLocal;

    /**
     * Creates a new instance of WeddingProjectResource
     */
    public WeddingProjectsResource() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<WeddingProject> getAllProjects() {
        List<WeddingProject> weddingProjects = weddingProjectSessionBeanLocal.getAllWeddingProject();
        
        for (WeddingProject w : weddingProjects) {
            
        }
    }

    @GET
    @Path("/{wProjectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectById(@PathParam("wProjectId") Long wProjectId) {
        try {

            WeddingProject wProject = weddingProjectSessionBeanLocal.getWeddingProject(wProjectId);

            GenericEntity<WeddingProject> entityToReturn = new GenericEntity<WeddingProject>(wProject) {
            };

            return Response.status(200).entity(entityToReturn).type(MediaType.APPLICATION_JSON).build();
        } catch (WeddingProjectNotFoundException e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Wedding Project with id " + wProjectId + " not found")
                    .build();
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

}