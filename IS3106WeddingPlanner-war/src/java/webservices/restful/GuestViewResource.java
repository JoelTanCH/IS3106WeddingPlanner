/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Guest;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.GuestSessionBeanLocal;
import util.exception.InvalidGetException;

/**
 * REST Web Service
 *
 * @author leomi
 */
@Path("guestview")
@RequestScoped
public class GuestViewResource {

    @Context
    private UriInfo context;
    @EJB
    private GuestSessionBeanLocal guestSBL;
    
    /**
     * Creates a new instance of GuestViewResource
     */
    public GuestViewResource() {
    }

    /**
     * Retrieves representation of an instance of webservices.restful.GuestViewResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGuestsFromWedding(@QueryParam("email") String email, @QueryParam("weddingId") Long weddingId) {
        System.out.println("email " + email);
        System.out.println("weddingId" + weddingId);
            Optional<Guest> guestId = guestSBL.getGuest(email, weddingId);
            JsonObject exception = Json.createObjectBuilder()
                    .add("Error", "Invalid Wedding ID")
                    .build();
      return guestId.map(g -> {
                          return Response.status(200).entity(g).type(MediaType.APPLICATION_JSON).build();
                    }).orElse(Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build());

    }


}
