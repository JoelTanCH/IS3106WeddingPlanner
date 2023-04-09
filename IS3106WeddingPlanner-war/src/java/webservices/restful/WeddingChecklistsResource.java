/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.WeddingChecklist;
import entity.WeddingParentTask;
import entity.WeddingSubtask;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
        WeddingChecklist checklistObject =  weddingChecklistBeanLocal.getWeddingChecklist(weddingChecklistId);
        
        checklistObject.setWeddingProject(null);
        
        List<WeddingParentTask> parentTasks = checklistObject.getWeddingParentTasks();
        for (WeddingParentTask parentTask : parentTasks) {
            
            parentTask.setWeddingChecklist(null);
            
            for (WeddingSubtask subTask : parentTask.getWeddingSubtasks()) {
                subTask.setWeddingParentTask(null);
            }
        }
        
        return checklistObject;
    }
    
    @POST
    @Path("/{wedding-checklist-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createParentTask(WeddingParentTask weddingTask, @PathParam("wedding-checklist-id") Long weddingChecklistId) {
        try {
            Long id = weddingChecklistBeanLocal.createParentTask(weddingTask, weddingChecklistId);
            JsonObject weddingBudgetListId = Json.createObjectBuilder().add("WEDDINGPARENTTASKID", id).build();
            return Response.status(200).entity(weddingBudgetListId).build();
        } catch (InvalidAssociationException ex) {
            JsonObject exception = Json.createObjectBuilder().add("Error", "Invalid Wedding").build();
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @POST
    @Path("/subtask/{parent-task-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSubtask(WeddingSubtask weddingTask, @PathParam("parent-task-id") Long weddingParentTaskId) {
        try {
            Long id = weddingChecklistBeanLocal.createSubtask(weddingTask, weddingParentTaskId);
            JsonObject weddingBudgetListId = Json.createObjectBuilder().add("WEDDINGSUBTASKID", id).build();
            return Response.status(200).entity(weddingBudgetListId).build();
        } catch (InvalidAssociationException ex) {
            JsonObject exception = Json.createObjectBuilder().add("Error", "Invalid Wedding").build();
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @GET
    @Path("/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WeddingParentTask> getAllParentTasks() {
        return weddingChecklistBeanLocal.getAllWeddingParentTasks();
    }
    
    @GET
    @Path("/subtasks")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WeddingSubtask> getAllSubTasks() {
        return weddingChecklistBeanLocal.getAllWeddingSubtasks();
    }
    
    @PUT
    @Path("/update/parentTask")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateParentTask(WeddingParentTask t) {
        try {
            weddingChecklistBeanLocal.updateParentTask(t);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Not Found").build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @PUT
    @Path("/update/subtask")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSubtask(WeddingSubtask t) {
        try {
            weddingChecklistBeanLocal.updateSubtask(t);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Not Found").build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @DELETE
    @Path("/task/{parent-task-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteParentTask(@PathParam("parent-task-id") Long taskId) {
        try {
            weddingChecklistBeanLocal.deleteParentTask(taskId);
            return Response.status(204).build();
        } catch(Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Item not found")
                    .build();
            
            return Response.status(404).entity(exception).build();
        }
    }
    
    @DELETE
    @Path("/subtask/{subtask-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSubtask(@PathParam("subtask-id") Long subtaskId) {
        try {
            weddingChecklistBeanLocal.deleteSubtask(subtaskId);
            return Response.status(204).build();
        } catch(Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Item not found")
                    .build();
            
            return Response.status(404).entity(exception).build();
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
//            WeddingSubtask t) {
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
//            WeddingSubtask t) {
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
//            WeddingSubtask t) {
//
//        weddingChecklistBeanLocal.removeTask(t);
//                
//    }    
}
