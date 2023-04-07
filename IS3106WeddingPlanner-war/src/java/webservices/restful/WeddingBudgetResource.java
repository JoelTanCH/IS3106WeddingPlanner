/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.WeddingBudgetItem;
import entity.WeddingBudgetList;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.WeddingBudgetSessionBeanLocal;
import util.exception.InvalidAssociationException;

/**
 * REST Web Service
 *
 * @author TUFGAMING
 */
@Path("LogisticsManagement/WeddingBudget")
@RequestScoped
public class WeddingBudgetResource {

    @Context
    private UriInfo context;

    @EJB
    private WeddingBudgetSessionBeanLocal weddingBudgetSessionBean;
    
    /**
     * Creates a new instance of WeddingBudgetResource
     */
    public WeddingBudgetResource() {
    }
    
    @POST
    @Path("/create/{wedding-project-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBudget(WeddingBudgetList budget, @PathParam("wedding-project-id") Long weddingProjectId) {
        try {
            Long id = weddingBudgetSessionBean.createBudget(budget, weddingProjectId);
            JsonObject weddingBudgetListId = Json.createObjectBuilder().add("WEDDINGBUDGETLISTID", id).build();
            return Response.status(200).entity(weddingBudgetListId).build();
        } catch (InvalidAssociationException ex) {
            JsonObject exception = Json.createObjectBuilder().add("Error", "Invalid Wedding").build();
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @POST
    @Path("/{wedding-budget-list-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createItem(WeddingBudgetItem item, @PathParam("wedding-budget-list-id") Long weddingBudgetListId) {
        try {
            Long id = weddingBudgetSessionBean.createItem(item, weddingBudgetListId);
            JsonObject weddingBudgetItemId = Json.createObjectBuilder().add("WEDDINGBUDGETITEMID", id).build();
            return Response.status(200).entity(weddingBudgetItemId).build();
        } catch (InvalidAssociationException ex) {
            JsonObject exception = Json.createObjectBuilder().add("Error", "Invalid Wedding").build();
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @GET
    @Path("/budgets/{wedding-project-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WeddingBudgetList> retrieveAllBudgets(@PathParam("wedding-project-id") Long weddingProjectId) {
        return weddingBudgetSessionBean.getBudgets(weddingProjectId);
    }
    
    @GET
    @Path("/budget/{wedding-budget-list-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public WeddingBudgetList retrieveBudget(@PathParam("wedding-budget-list-id") Long weddingBudgetListId) {
        return weddingBudgetSessionBean.getBudget(weddingBudgetListId);
    }
    
    @PUT
    @Path("/update/budget")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBudget(WeddingBudgetList budget) {
        try {
            weddingBudgetSessionBean.updateBudget(budget);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Not Found").build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @PUT
    @Path("/update/item")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItem(WeddingBudgetItem item) {
        try {
            weddingBudgetSessionBean.updateItem(item);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Not Found").build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @GET
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WeddingBudgetItem> retrieveAllItems() {
        return weddingBudgetSessionBean.retrieveAllItems();
    }
    
    @GET
    @Path("/{item-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveItem(@PathParam("item-id") Long itemId) {
        try {
            WeddingBudgetItem i = weddingBudgetSessionBean.retrieveItem(itemId);
            return Response.status(200).entity(i).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not Found")
                    .build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @DELETE
    @Path("/{item-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteItem(@PathParam("item-id") Long itemId) {
        try {
            weddingBudgetSessionBean.deleteItem(itemId);
            return Response.status(204).build();
        } catch(Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Item not found")
                    .build();
            
            return Response.status(404).entity(exception).build();
        }
    }
    
    @POST
    @Path("/totalCost")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BigDecimal totalCost() {
        return weddingBudgetSessionBean.totalCost();
    }
    
    @POST
    @Path("/totalPaid")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BigDecimal totalPaid() {
        return weddingBudgetSessionBean.totalPaid();
    }

}
