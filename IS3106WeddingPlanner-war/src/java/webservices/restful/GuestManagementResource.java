/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Guest;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.NoResultException;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.GuestSessionBeanLocal;
import util.exception.InvalidAssociationException;
import util.exception.InvalidGetException;
import util.exception.InvalidUpdateException;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import jwt.JWTSessionBeanLocal;
/**
 * REST Web Service
 *
 * @author leomi
 */
@Path("guestmanagement")
@RequestScoped
public class GuestManagementResource {


    @EJB
    private GuestSessionBeanLocal guestSBL;

    @Context
    private UriInfo context;
    
    @EJB
    private JWTSessionBeanLocal jwtSBL;

    /**
     * Creates a new instance of GuestmanagementResource
     */
    public GuestManagementResource() {
    }

    /**
     * Retrieves representation of an instance of
     * webservices.restful.GuestmanagementResource
     *
     * @return an instance of java.lang.String
     */

    /**
     * PUT method for updating or creating an instance of
     * GuestmanagementResource
     *
     * @param content representation for the resource
     */

    
    @POST
    @Path("/{wId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGuest(Guest g, @PathParam("wId") Long wId) {
        try {
            Long id = guestSBL.createGuest(g, wId);
            JsonObject guestId = Json.createObjectBuilder().add("GUESTID", id).build();
            return Response.status(200).entity(guestId).build();
        } catch (InvalidAssociationException ex) {
            JsonObject exception = Json.createObjectBuilder().add("Error", "Invalid Wedding").build();
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGuest(Guest guest) {
        try {
            guestSBL.updateGuest(guest);
            return Response.status(204).build();
        } catch (InvalidUpdateException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("Error", "Update Invalid")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end editCustomer

    @PUT
    @Path("/changersvp")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGuestsRSVP(List<Guest> guests) {
        try {
            guestSBL.updateGuestsRSVP(guests);
            return Response.status(204).build();
        } catch (InvalidUpdateException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("Error", "One or more invalid guest rsvp updates")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end editCustomer
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGuestsFromWedding(@QueryParam("wId") Long weddingId) {
        try {
            List<Guest> guests = guestSBL.getGuests(weddingId);
            guests.forEach(guest -> guest.setWeddingProject(null));
            guests.forEach(guest -> {
                if (guest.getGuestTable() != null) {
                    guest.getGuestTable().setGuests(null);
                    guest.getGuestTable().setWeddingProject(null);
                }
            });
            GenericEntity<List<Guest>> entity = new GenericEntity<List<Guest>>(guests) { };
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
    @Path("/{guestId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGuest(@PathParam("guestId") Long guestId) {
        try {
            guestSBL.deleteGuest(guestId);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("Error", "Guest not found")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    } //end deleteField
    
    @GET
    @Path("/simulateLogin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveToken(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
            String token = jwtSBL.generateToken("ADMIN");
            JsonObject successMsg = Json.createObjectBuilder().add("TOKEN", token).build();
            return Response.status(200).entity(successMsg).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("Error", "Guest not found")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    } //end deleteField
    
    @POST
    @Path("/simulateLogin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveToken(@QueryParam("token") String token) {
        try {
            if (jwtSBL.verifyToken(token).equals("ADMIN")) {
                return Response.status(204).build();
            } else {
                throw new InvalidGetException();
            }
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("Error", "Token INVALID")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    } //end deleteField


}
