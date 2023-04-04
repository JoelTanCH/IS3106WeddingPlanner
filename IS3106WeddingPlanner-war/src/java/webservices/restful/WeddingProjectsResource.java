/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.WeddingProject;
import error.WeddingOrganiserNotFoundException;
import error.WeddingProjectNotFoundException;
import java.util.List;
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
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
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

    private void nullifyBidirectionalWeddingProject(WeddingProject w) {
        w.setRequests(null);
        w.setWeddingItineraries(null);
        w.setWeddingOrganiser(null);
        w.setWeddingBudgetList(null);
        w.setTables(null);
        w.setGuests(null);
        w.setWeddingChecklist(null);
        w.setWeddingOrganiser(null);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<WeddingProject> getAllProjects() {
        List<WeddingProject> weddingProjects = weddingProjectSessionBeanLocal.getAllWeddingProject();

        for (WeddingProject w : weddingProjects) {
            nullifyBidirectionalWeddingProject(w);
        }

        return weddingProjects;
    }

    @GET
    @Path("/{wedding-project-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectById(@PathParam("wedding-project-id") Long wProjectId) {
        try {

            WeddingProject wProject = weddingProjectSessionBeanLocal.getWeddingProject(wProjectId);

            nullifyBidirectionalWeddingProject(wProject);

            GenericEntity<WeddingProject> entityToReturn = new GenericEntity<WeddingProject>(wProject) {
            };

            return Response.status(200).entity(entityToReturn).type(MediaType.APPLICATION_JSON).build();
        } catch (WeddingProjectNotFoundException e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Wedding Project with id " + wProjectId + " not found")
                    .build();
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("getByOrgId")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProjectsByWeddingOrganiserId(@QueryParam("wedding-organiser-id") Long wOrganiserId) {

        try {
            List<WeddingProject> projects = weddingProjectSessionBeanLocal.getAllWeddingProjectbyOrganiser(wOrganiserId);

            for (WeddingProject w : projects) {
                nullifyBidirectionalWeddingProject(w);
            }

            GenericEntity<List<WeddingProject>> entityToReturn = new GenericEntity<List<WeddingProject>>(projects) {
            };

            return Response.status(200).entity(entityToReturn).type(MediaType.APPLICATION_JSON).build();

            // error shouldnt even happen because an empty list would be returned
        } catch (WeddingOrganiserNotFoundException e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Wedding Projects from Wedding Organiser with id " + wOrganiserId + " not found")
                    .build();
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createWeddingProject(@QueryParam("wedding-organiser-id") Long organiserId, WeddingProject w) {
        weddingProjectSessionBeanLocal.createWeddingProject(organiserId, w);
        return Response.status(200).build();
    }

    // should work in updating all the bidirectional stuff as well, but need to test in the future
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWeddingProject(WeddingProject w) {
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

}
