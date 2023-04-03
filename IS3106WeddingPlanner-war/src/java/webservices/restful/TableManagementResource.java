/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Guest;
import entity.GuestTable;
import entity.Stage;
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
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.GuestTableSessionBeanLocal;
import session.StageSessionBeanLocal;
import util.exception.InvalidAssociationException;
import util.exception.InvalidDeleteException;
import util.exception.InvalidGetException;

/**
 * REST Web Service
 *
 * @author leomi
 */
@Path("tablemanagement")
@RequestScoped
public class TableManagementResource {

    @EJB
    private GuestTableSessionBeanLocal tableSBL;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TableManagementResource
     */
    public TableManagementResource() {
    }

    /**
     * Retrieves representation of an instance of webservices.restful.TableManagementResource
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/{wId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTable(GuestTable t, @PathParam("wId") Long wId) {
        try {
            Long id = tableSBL.createGuestTable(t, wId);
            JsonObject guestId = Json.createObjectBuilder().add("GUESTID", id).build();
            return Response.status(200).entity(guestId).build();
        } catch (InvalidAssociationException ex) {
            JsonObject exception = Json.createObjectBuilder().add("Error", "Invalid Wedding").build();
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
 
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTablesFromWedding(@QueryParam("weddingId") Long weddingId) {
        try {
            List<GuestTable> tables = tableSBL.getGuestTables(weddingId);
            tables.forEach(table -> {
                table.setWeddingProject(null);
                table.getGuests().forEach(g -> {
                    g.setGuestTable(null);
                    g.setWeddingProject(null);
                });
            });
            GenericEntity<List<GuestTable>> entity = new GenericEntity<List<GuestTable>>(tables) { };
            return Response.status(200).entity(entity).type(MediaType.APPLICATION_JSON).build();
        } catch (InvalidGetException ex) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("Error", "Invalid Wedding ID")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();

        }

    }
    
    @DELETE
    @Path("/{tableId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTable(@PathParam("tableId") Long tableId) {
        try {
            tableSBL.deleteGuestTable(tableId);
            return Response.status(204).build();
        } catch (InvalidDeleteException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("Error", "Table not found")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    } //end deleteField
    
    @POST
    @Path("/{tableId}/guest/{guestId}") 
    public Response addGuestToTable(@PathParam("tableId") Long tableId, @PathParam("guestId") Long guestId) {
        try {
            tableSBL.addGuestToTable(guestId, tableId);
            return Response.status(204).build();
        } catch (InvalidAssociationException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("Error", "Invalid Association")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    }
    
    @PUT
    @Path("/{tableId}/guest/{guestId}") 
    public Response removeGuestFromTable(@PathParam("tableId") Long tableId, @PathParam("guestId") Long guestId) {
        try {
            tableSBL.removeGuestFromTable(guestId, tableId);
            return Response.status(204).build();
        } catch (InvalidAssociationException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("Error", "Invalid Dissociation")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    }
    
    @PUT
    @Path("/saveTables/{wId}") 
    public Response updateTables(List<GuestTable> tables,  @PathParam("wId") Long wId) {
        try {
            tableSBL.updateGuestTables(tables, wId);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("Error", "Saving Tables Error")
                    .build();
            return Response.status(404).entity(exception).build();        
        }
    }
    

    
    

}
