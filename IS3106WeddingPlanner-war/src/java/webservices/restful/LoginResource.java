/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Admin;
import entity.Vendor;
import entity.WeddingOrganiser;
import java.io.InputStream;
import java.math.BigDecimal;
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
import javax.json.JsonReader;
import javax.ws.rs.POST;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import jwt.JWTSessionBeanLocal;
import session.AdminSessionBeanLocal;
import session.VendorSessionBeanLocal;
import session.WeddingOrganiserSessionBeanLocal;

/**
 * REST Web Service
 *
 * @author wangp
 */
@Path("login")
@RequestScoped
public class LoginResource {

    @EJB
    AdminSessionBeanLocal adminSessionBeanLocal;

    @EJB
    VendorSessionBeanLocal vendorSessionBeanLocal;

    @EJB
    WeddingOrganiserSessionBeanLocal weddingOrganiserSessionBeanLocal;

    @EJB
    JWTSessionBeanLocal jWTSessionBeanLocal;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LoginResource
     */
    public LoginResource() {
    }

    /**
     * Retrieves representation of an instance of
     * webservices.restful.LoginResource
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginAdmin(InputStream input) {
        // Actually no need for InputStream, can just use Admin or Vendor or User
        // since the attribute names in the json object sent is the same
        // however good to use this JsonReader to read 'custom' json objects
        //
        // Parse the JSON data from the input stream
        JsonReader reader = Json.createReader(input);
        JsonObject jsonObject = reader.readObject();
        reader.close();

        // Extract the values of the username and password attributes
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        List<Admin> gotAdminsByUsernamePassword = adminSessionBeanLocal.getAdminsByUsernamePassword(username, password);

        if (gotAdminsByUsernamePassword.isEmpty()) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Admin with username & password doesn't exist")
                    .build();

            // 404 is not found
            return Response.status(404).entity(exception).build();
        } else {
            // if list isnt empty, build the token
            Admin adminFound = gotAdminsByUsernamePassword.get(0);

            // if admin is banned return error as well
            if (adminFound.isIsBanned()) {
                JsonObject exception = Json.createObjectBuilder().add("error", "Admin found is banned")
                        .build();

                // 403 is forbidden (i think)
                return Response.status(403).entity(exception).build();
            }
            JsonObject tokenInfo = Json.createObjectBuilder()
                    .add("userType", "ADMIN")
                    .add("userId", "" + adminFound.getUserId())
                    .add("username", adminFound.getUsername())
                    .add("email", adminFound.getEmail())
                    .build();

            String tokenGenerated = jWTSessionBeanLocal.generateToken(tokenInfo.toString());
//            GenericEntity<String> entityToReturn = new GenericEntity<List<Admin>>(gotAdminsByUsernamePassword) {

            // thankfully we can just pass in a String as an entity and it works!
            return Response.status(200).entity(tokenGenerated).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of LoginResource
     *
     * @param content representation for the resource
     */
    @POST
    @Path("/wedding-organiser")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginWeddingOrganiser(InputStream input) {
        JsonReader reader = Json.createReader(input);
        JsonObject jsonObject = reader.readObject();
        reader.close();

        // Extract the values of the username and password attributes
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        List<WeddingOrganiser> gotWeddingOrganisers
                = weddingOrganiserSessionBeanLocal.getWeddingOrganisersByUsernamePassword(username, password);

        if (gotWeddingOrganisers.isEmpty()) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Wedding Organiser with username & password doesn't exist")
                    .build();

            // 401 is invalid credentials
            return Response.status(401).entity(exception).build();
        } else {
            // if list isnt empty, build the token
            WeddingOrganiser woFound = gotWeddingOrganisers.get(0);

            if (woFound.isIsBanned()) {
                JsonObject exception = Json.createObjectBuilder().add("error", "Wedding Organiser found is banned.")
                        .build();

                // 403 is forbidden (i think)
                return Response.status(403).entity(exception).build();
            }
            JsonObject tokenInfo = Json.createObjectBuilder()
                    .add("userType", "WEDDING-ORGANISER")
                    .add("userId", "" + woFound.getUserId())
                    .add("username", woFound.getUsername())
                    .add("email", woFound.getEmail())
                    .build();

            String tokenGenerated = jWTSessionBeanLocal.generateToken(tokenInfo.toString());
//            GenericEntity<String> entityToReturn = new GenericEntity<List<Admin>>(gotAdminsByUsernamePassword) {

            // thankfully we can just pass in a String as an entity and it works!
            return Response.status(200).entity(tokenGenerated).build();
        }
    }

    @POST
    @Path("/vendor")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginVendor(InputStream input) {
        JsonReader reader = Json.createReader(input);
        JsonObject jsonObject = reader.readObject();
        reader.close();

        // Extract the values of the username and password attributes
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        List<Vendor> gotVendors = vendorSessionBeanLocal.getVendorsByUsernamePassword(username, password);
        if (gotVendors.isEmpty()) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Vendor with username & password doesn't exist")
                    .build();

            // 401 is invalid credentials
            return Response.status(401).entity(exception).build();
        } else {
            // if list isnt empty, build the token
            Vendor vendorFound = gotVendors.get(0);

            if (vendorFound.isIsBanned()) {
                JsonObject exception = Json.createObjectBuilder().add("error", "Vendor found is banned.")
                        .build();

                // 403 is forbidden (i think)
                return Response.status(403).entity(exception).build();
            }
            JsonObject tokenInfo = Json.createObjectBuilder()
                    .add("userType", "VENDOR")
                    .add("userId", "" + vendorFound.getUserId())
                    .add("username", vendorFound.getUsername())
                    .add("email", vendorFound.getEmail())
                    .build();

            String tokenGenerated = jWTSessionBeanLocal.generateToken(tokenInfo.toString());
//            GenericEntity<String> entityToReturn = new GenericEntity<List<Admin>>(gotAdminsByUsernamePassword) {

            // thankfully we can just pass in a String as an entity and it works!
            return Response.status(200).entity(tokenGenerated).build();
        }
    }

}
