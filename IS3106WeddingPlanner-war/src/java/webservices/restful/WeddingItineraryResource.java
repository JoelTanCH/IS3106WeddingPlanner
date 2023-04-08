/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Guest;
import entity.WeddingItinerary;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.WeddingItinerarySessionBeanLocal;
import util.exception.InvalidAssociationException;

/**
 * REST Web Service
 *
 * @author TUFGAMING
 */
@Path("/LogisticsManagement/WeddingItinerary")
@RequestScoped
public class WeddingItineraryResource {

    @Context
    private UriInfo context;

    @EJB
    private WeddingItinerarySessionBeanLocal weddingItinerarySessionBean;
    
    /**
     * Creates a new instance of WeddingItineraryResource
     */
    public WeddingItineraryResource() {
    }
    
    @POST
    @Path("/create/{wedding-project-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewItinerary(WeddingItinerary i, @PathParam("wedding-project-id") Long weddingProjectId) {
        try {
            Long id = weddingItinerarySessionBean.createNewItinerary(i, weddingProjectId);
            JsonObject weddingItineraryId = Json.createObjectBuilder().add("WEDDINGITINERARYID", id).build();
            return Response.status(200).entity(weddingItineraryId).build();
        } catch (InvalidAssociationException ex) {
            JsonObject exception = Json.createObjectBuilder().add("Error", "Invalid Wedding").build();
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @GET
    @Path("/itineraries")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WeddingItinerary> getAllItineraries() {
        return weddingItinerarySessionBean.getAllItineraries();
//        List<JsonObject> objects = new ArrayList<JsonObject>();
//        for (WeddingItinerary itinerary : itineraries) {
//            JsonObject weddingItineraryId = Json.createObjectBuilder().add("WEDDINGITINERARYID", itinerary.getWeddingItineraryId()).build();
//            objects.add(weddingItineraryId);
//        }
//        return objects;
    }
    
    @GET
    @Path("/{wedding-itinerary-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItinerary(@PathParam("wedding-itinerary-id") Long weddingItineraryId) {
        try {
            WeddingItinerary i = weddingItinerarySessionBean.getItinerary(weddingItineraryId);
            return Response.status(200).entity(i).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not Found")
                    .build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    @GET
    @Path("/wedding/{weddingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWeddingItinerary(@PathParam("weddingId") Long wId) {
        try {
            List<WeddingItinerary> list = weddingItinerarySessionBean.getWeddingItinerary(wId);
            GenericEntity<List<WeddingItinerary>> entity = new GenericEntity<List<WeddingItinerary>>(list) { };

            return Response.status(200).entity(entity).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not Found")
                    .build();
          
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    @PUT
    @Path("/update/itinerary")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItinerary(WeddingItinerary itinerary) {
        try {
            weddingItinerarySessionBean.updateItinerary(itinerary);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Not Found").build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @DELETE
    @Path("/{wedding-itinerary-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteItinerary(@PathParam("wedding-itinerary-id") Long weddingItineraryId) {
        try {
            weddingItinerarySessionBean.deleteItinerary(weddingItineraryId);
            return Response.status(204).build();
        } catch(Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Item not found")
                    .build();
            
            return Response.status(404).entity(exception).build();
        }
    }

//    /**
//     * Retrieves representation of an instance of webservices.restful.WeddingItineraryResource
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
//     * PUT method for updating or creating an instance of WeddingItineraryResource
//     * @param content representation for the resource
//     */
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void putJson(String content) {
//    }
}
