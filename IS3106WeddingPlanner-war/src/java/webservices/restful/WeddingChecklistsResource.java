/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.WeddingBudgetList;
import entity.WeddingChecklist;
import entity.WeddingTask;
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
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.WeddingChecklistBeanLocal;
import util.exception.InvalidAssociationException;

/**
 * REST Web Service
 *
 * @author wangp
 */
// This resource doesn't retrieve the actual WeddingChecklist object
// Because at this point in the app, the user should already have the WeddingProject object
// which contains the WeddingChecklist object, and so on.
// This resource involves mainly updating of the WeddingChecklist object into the database.
@Path("LogisticsManagement/WeddingChecklist")
@RequestScoped
public class WeddingChecklistsResource {

    @Context
    private UriInfo context;

    @EJB
    WeddingChecklistBeanLocal weddingChecklistBeanLocal;
    
    /**
     * Creates a new instance of WeddingChecklistsResource
     */
    public WeddingChecklistsResource() {
    }
    
    @POST
    @Path("/create/{wedding-project-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createWeddingChecklist(WeddingChecklist weddingChecklist, @PathParam("wedding-project-id") Long weddingProjectId) {
        try {
            Long id = weddingChecklistBeanLocal.createWeddingChecklist(weddingChecklist, weddingProjectId);
            JsonObject weddingBudgetListId = Json.createObjectBuilder().add("WEDDINGCHECKLISTID", id).build();
            return Response.status(200).entity(weddingBudgetListId).build();
        } catch (InvalidAssociationException ex) {
            JsonObject exception = Json.createObjectBuilder().add("Error", "Invalid Wedding").build();
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @GET
    @Path("/checklists")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WeddingChecklist> getAllWeddingChecklists() {
        return weddingChecklistBeanLocal.getAllWeddingChecklists();
    }
    
    @GET
    @Path("/checklist/{wedding-checklist-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public WeddingChecklist getWeddingChecklist(@PathParam("wedding-checklist-id") Long weddingChecklistId) {
        return weddingChecklistBeanLocal.getWeddingChecklist(weddingChecklistId);
    }
    
    @POST
    @Path("/{wedding-checklist-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(WeddingTask weddingTask, @PathParam("wedding-checklist-id") Long weddingChecklistId) {
        try {
            Long id = weddingChecklistBeanLocal.createTask(weddingTask, weddingChecklistId);
            JsonObject weddingBudgetListId = Json.createObjectBuilder().add("ID", id).build();
            return Response.status(200).entity(weddingBudgetListId).build();
        } catch (InvalidAssociationException ex) {
            JsonObject exception = Json.createObjectBuilder().add("Error", "Invalid Wedding").build();
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
//    @GET
//    @Path("/tasks")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<WeddingTask> getAllParentTasks() {
//        return weddingChecklistBeanLocal.getAllParentTasks();
//    }
//    
//    @GET
//    @Path("/subtasks")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<WeddingTask> getAllSubTasks() {
//        return weddingChecklistBeanLocal.getAllSubTasks();
//    }
    
    @PUT
    @Path("/update/task")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTask(WeddingTask t) {
        try {
            weddingChecklistBeanLocal.updateTask(t);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Not Found").build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
//    @GET
//    @Path("/{wedding-checklist-id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public WeddingChecklist getChecklist(@PathParam("wedding-checklist-id") Long checklistId) {
//        return weddingChecklistBeanLocal.getWeddingChecklist(checklistId); 
//    }
//    
//    @POST
//    @Path("/{wedding-checklist-id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void createTask(@PathParam("wedding-checklist-id") Long checklistId,
//            WeddingTask t) {
//
//        weddingChecklistBeanLocal.createTask(t, checklistId);
//                
//    }
//    
//    // actually no need id, but i think for consistency's sake just keep the url?
//    // no need id since we are modifying Task directly which is already connected to a Checklist
//    @PUT
//    @Path("/{wedding-checklist-id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void updateTask(@PathParam("wedding-checklist-id") Long checklistId,
//            WeddingTask t) {
//
//        weddingChecklistBeanLocal.updateTask(t);
//                
//    }
//    
//    
//    // same as above, no need id
//    @DELETE
//    @Path("/{wedding-checklist-id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void removeTask(@PathParam("wedding-checklist-id") Long checklistId,
//            WeddingTask t) {
//
//        weddingChecklistBeanLocal.removeTask(t);
//                
//    }    
}
