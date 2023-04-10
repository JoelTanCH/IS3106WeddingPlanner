/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Request;
import entity.Transaction;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.RequestSessionBeanLocal;
import session.TransactionSessionBeanLocal;

/**
 * REST Web Service
 *
 * @author wangp
 */
@Path("transactions")
@RequestScoped
public class TransactionsResource {

    @EJB
    RequestSessionBeanLocal requestSessionBeanLocal;

    @EJB
    TransactionSessionBeanLocal transactionSessionBeanLocal;

    @Context
    private UriInfo context;

    public TransactionsResource() {
    }

    @GET
    @Path("/getByRequest/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactionByRequestId(@PathParam("id") Long requestId) {
        try {
            Request request = requestSessionBeanLocal.retrieveRequest(requestId);
            Transaction trans = request.getTransaction();
            trans.setRequest(null);

            GenericEntity<Transaction> entityToReturn = new GenericEntity<Transaction>(trans) {
            };

            return Response.status(200).entity(entityToReturn).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Probably request of id " + requestId + " has no transaction")
                    .build();

            // 400 is error; invalid request
            return Response.status(400).entity(exception).build();

        }
    }

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public void createRequest(Request request) {
//        requestSessionBeanLocal.createRequest(request);
//    }
//    @GET
//    @Path("{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getTransactionById(@PathParam("id") Long transactionId) {
//        try {
//            Request request = requestSessionBeanLocal.retrieveRequest(requestId);
//            JsonObjectBuilder builder = Json.createObjectBuilder();
//            //Request start and end need to be determined? + Add venue.
//            builder.add("requestDetails", request.getRequestDetails())
//                    .add("requestStart", request.getRequestDate().toLocaleString())
//                    .add("quotationURL", request.getQuotationURL());
//
//            if (request.getTransaction() != null) {
//                builder.add("isPaid", request.getTransaction().isIsPaid());
//            }
//
//            if (request.getQuotedPrice() != null) {
//                builder.add("quotedPrice", request.getQuotedPrice());
//            }
//            if (request.getIsAccepted() != null) {
//                builder.add("isAccepted", request.getIsAccepted());
//            }
//
//            //.add("weddingName", request.getWeddingProject().getName())
//            //.add("weddingOrganiserName", request.getWeddingProject().getWeddingOrganiser().getUsername())
//            return Response.status(200).entity(builder.build()).type(MediaType.APPLICATION_JSON).build();
//        } catch (NoResultException e) {
//
//            //Template to throw error. Can change
//            JsonObject exception = Json.createObjectBuilder()
//                    .add("error", "Not found")
//                    .build();
//            return Response.status(404).entity(exception)
//                    .type(MediaType.APPLICATION_JSON).build();
//        }
//
//    }
}
