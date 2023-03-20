/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Request;
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
import javax.persistence.NoResultException;
import javax.ws.rs.PathParam;
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

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRequests(@PathParam("id") Long requestId) {
        try {
            Request request = requestSessionBeanLocal.retrieveRequest(requestId);
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
}
