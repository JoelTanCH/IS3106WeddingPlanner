/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Request;
import java.math.BigDecimal;
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
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;
import javax.persistence.NoResultException;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.RequestSessionBeanLocal;

/**
 * REST Web Service
 *
 * @author Ezekiel Ang
 */
@Path("requests")
@RequestScoped
public class RequestsResource {

    @EJB
    RequestSessionBeanLocal requestSessionBeanLocal;

    @Context
    private UriInfo context;

    public RequestsResource() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createRequest(Request request) {
        requestSessionBeanLocal.createRequest(request);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRequests(@PathParam("id") Long requestId) {
        try {
            Request request = requestSessionBeanLocal.retrieveRequest(requestId);
            request.setVendor(null);
            request.setWeddingProject(null);
            return Response.status(200).entity(request).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {

            //Template to throw error. Can change
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("vendorRequests/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVendorRequests(@PathParam("id") Long vendorId) {
        try {
            //add start end info
            List<Request> requests = requestSessionBeanLocal.retrieveVendorRequests(vendorId);
            for (Request req : requests) {
                //Disassociation
                req.setVendor(null);
                if (req.getTransaction() != null) {
                    req.getTransaction().setRequest(null);
                }
                req.getWeddingProject().setCompleted(null);
                req.getWeddingProject().setDescription(null);
                req.getWeddingProject().setGuests(null);
                req.getWeddingProject().setRequests(null);
                req.getWeddingProject().setTables(null);
                req.getWeddingProject().setWeddingBudgetList(null);
                req.getWeddingProject().setWeddingChecklist(null);
                req.getWeddingProject().setWeddingItineraries(null);
                req.getWeddingProject().setWeddingOrganiser(null);
                //dis ev except timings
            }
            return Response.status(200).entity(requests).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {

            //Template to throw error. Can change
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("vendorRequests/accepted/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAcceptedVendorRequests(@PathParam("id") Long vendorId) {
        try {
            //add start end info
            List<Request> requests = requestSessionBeanLocal.retrieveAcceptedVendorRequests(vendorId);
            for (Request req : requests) {
                //Disassociation
                req.setVendor(null);
                if (req.getTransaction() != null) {
                    req.getTransaction().setRequest(null);
                }
                req.getWeddingProject().setCompleted(null);
                req.getWeddingProject().setDescription(null);
                req.getWeddingProject().setGuests(null);
                req.getWeddingProject().setRequests(null);
                req.getWeddingProject().setTables(null);
                req.getWeddingProject().setWeddingBudgetList(null);
                req.getWeddingProject().setWeddingChecklist(null);
                req.getWeddingProject().setWeddingItineraries(null);
                req.getWeddingProject().setWeddingOrganiser(null);
                //dis ev except timings
            }
            return Response.status(200).entity(requests).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {

            //Template to throw error. Can change
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @PUT
    @Path("vendorRequests/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void respondToVendorRequest(JsonStructure json, @PathParam("id") Long requestId) {
        Boolean isAccepted = Boolean.valueOf(json.getValue("/isAccepted").toString());
        if (isAccepted) {
            requestSessionBeanLocal.acceptRequest(requestId);
        } else {
            requestSessionBeanLocal.rejectRequest(requestId);
        }

    }

    @PUT
    @Path("vendorRequests/setPrice/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void setPriceVendorRequest(JsonStructure json, @PathParam("id") Long requestId) {
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(json.getValue("/price").toString()));
        requestSessionBeanLocal.setNewRequestPrice(requestId, price);

    }

    @GET
    @Path("payRequest/{id}")
    public void payVendorRequest(@PathParam("id") Long requestId) {
        requestSessionBeanLocal.payRequest(requestId);
    }

    @GET
    @Path("/checkSchedule")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkSchedule(@QueryParam("requestId") Long requestId, @QueryParam("vendorId") Long vendorId) {
        Request req = requestSessionBeanLocal.retrieveRequest(requestId);
        Long clashes = requestSessionBeanLocal.checkSchedule(vendorId, requestId);
        System.out.println(clashes);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("clashes", clashes);
        builder.add("clashDate", req.getWeddingProject().getWeddingDate().toLocaleString());
        return Response.status(200).entity(builder.build()).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("request/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRequest(@PathParam("id") Long requestId) {
        try {
            Request request = requestSessionBeanLocal.retrieveRequest(requestId);
            JsonObjectBuilder builder = Json.createObjectBuilder();
            //Request start and end need to be determined? + Add venue.
            builder.add("requestDetails", request.getRequestDetails())
                    .add("requestDate", request.getWeddingProject().getWeddingDate().toLocaleString())
                    .add("requestStart", request.getWeddingProject().getWeddingStartTime().toLocaleString())
                    .add("quotationURL", request.getQuotationURL());
            if (request.getWeddingProject().getWeddingEndTime() != null) {
                builder.add("requestEnd", request.getWeddingProject().getWeddingEndTime().toLocaleString());
            }
            
            if (request.getTransaction() != null) {
                builder.add("isPaid", request.getTransaction().isIsPaid());
            }

            if (request.getQuotedPrice() != null) {
                builder.add("quotedPrice", request.getQuotedPrice());
            }
            if (request.getIsAccepted() != null) {
                builder.add("isAccepted", request.getIsAccepted());
            }

            builder.add("weddingName", request.getWeddingProject().getName());
            builder.add("weddingOrganiserName", request.getWeddingProject().getWeddingOrganiser().getUsername());
            builder.add("venue", request.getWeddingProject().getVenue());
            return Response.status(200).entity(builder.build()).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {

            //Template to throw error. Can change
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }

    }
}
