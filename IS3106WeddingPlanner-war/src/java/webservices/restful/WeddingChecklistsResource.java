/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.WeddingChecklist;
import entity.WeddingTask;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import session.WeddingChecklistBeanLocal;

/**
 * REST Web Service
 *
 * @author wangp
 */
// This resource doesn't retrieve the actual WeddingChecklist object
// Because at this point in the app, the user should already have the WeddingProject object
// which contains the WeddingChecklist object, and so on.
// This resource involves mainly updating of the WeddingChecklist object into the database.
@Path("wedding-checklists")
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

    
    
    // actually shouldnt need this but i left it here anyway
    @GET
    @Path("/{wedding-checklist-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public WeddingChecklist getChecklist(@PathParam("wedding-checklist-id") Long checklistId) {
        return weddingChecklistBeanLocal.getWeddingChecklist(checklistId); // do null checK? but unlikely to have error xD
    }
    
    @POST
    @Path("/{wedding-checklist-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createTask(@PathParam("wedding-checklist-id") Long checklistId,
            WeddingTask t) {

        weddingChecklistBeanLocal.createTask(t, checklistId);
                
    }
    
    // actually no need id, but i think for consistency's sake just keep the url?
    // no need id since we are modifying Task directly which is already connected to a Checklist
    @PUT
    @Path("/{wedding-checklist-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateTask(@PathParam("wedding-checklist-id") Long checklistId,
            WeddingTask t) {

        weddingChecklistBeanLocal.updateTask(t);
                
    }
    
    
    // same as above, no need id
    @DELETE
    @Path("/{wedding-checklist-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void removeTask(@PathParam("wedding-checklist-id") Long checklistId,
            WeddingTask t) {

        weddingChecklistBeanLocal.removeTask(t);
                
    }    
//
//    /**
//     * Retrieves representation of an instance of
//     * webservices.restful.WeddingChecklistsResource
//     *
//     * @return an instance of java.lang.String
//     */
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getJson() {
//        //TODO return proper representation object
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * PUT method for updating or creating an instance of
//     * WeddingChecklistsResource
//     *
//     * @param content representation for the resource
//     */
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void putJson(String content) {
//    }
}
