/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import java.util.Set;

/**
 *
 * @author wangp
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends javax.ws.rs.core.Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(webservices.restful.AdminsResource.class);
        resources.add(webservices.restful.CORSFilter.class);
        resources.add(webservices.restful.GuestManagementResource.class);
        resources.add(webservices.restful.GuestViewResource.class);
        resources.add(webservices.restful.LoginResource.class);
        resources.add(webservices.restful.RequestsResource.class);
        resources.add(webservices.restful.StageResource.class);
        resources.add(webservices.restful.TableManagementResource.class);
        resources.add(webservices.restful.TransactionsResource.class);
        resources.add(webservices.restful.VendorsResource.class);
        resources.add(webservices.restful.WeddingBudgetResource.class);
        resources.add(webservices.restful.WeddingChecklistsResource.class);
        resources.add(webservices.restful.WeddingItineraryResource.class);
        resources.add(webservices.restful.WeddingOrganisersResource.class);
        resources.add(webservices.restful.WeddingProjectsResource.class);
    }

}
