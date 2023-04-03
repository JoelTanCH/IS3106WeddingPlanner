/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Stage;
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
import session.StageSessionBeanLocal;
import util.exception.InvalidAssociationException;
import util.exception.InvalidGetException;

/**
 * REST Web Service
 *
 * @author leomi
 */
@Path("stage")
@RequestScoped
public class StageResource {
    @EJB
    private StageSessionBeanLocal stageSBL;
    @Context
    private UriInfo context;
    
    @POST
    @Path("/{wId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStage(Stage s, @PathParam("wId") Long wId) {
        try {
            Long id = stageSBL.createStage(s, wId);
            JsonObject guestId = Json.createObjectBuilder().add("STAGEID", id).build();
            return Response.status(200).entity(guestId).build();
        } catch (InvalidAssociationException ex) {
            JsonObject exception = Json.createObjectBuilder().add("Error", "Invalid Wedding").build();
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    /**
     * Creates a new instance of StageResource
     */
    public StageResource() {
    }

    /**
     * Retrieves representation of an instance of webservices.restful.StageResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStagesFromWedding(@QueryParam("weddingId") Long weddingId) {
        try {
            List<Stage> stages = stageSBL.getStages(weddingId);
            GenericEntity<List<Stage>> entity = new GenericEntity<List<Stage>>(stages) { };
            return Response.status(200).entity(entity).type(MediaType.APPLICATION_JSON).build();
        } catch (InvalidGetException ex) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("Error", "Invalid Wedding ID")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();

        }
    }
    @PUT
    @Path("/saveStages/{wId}") 
    public Response updateStages(List<Stage> stages,  @PathParam("wId") Long wId) {
        try {
            stages.forEach(System.out::println);
            stageSBL.updateStages(stages, wId);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("Error", "Saving Stages Error")
                    .build();
            return Response.status(404).entity(exception).build();        
        }
    }
}
