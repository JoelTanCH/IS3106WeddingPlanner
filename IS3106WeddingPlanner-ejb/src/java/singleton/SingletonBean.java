/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singleton;

import entity.Admin;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.AdminSessionBeanLocal;

/**
 *
 * @author wangp
 */
@Singleton
@LocalBean
@Startup
public class SingletonBean {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;

    @EJB
    private AdminSessionBeanLocal adminSessionBean;

    @PostConstruct
    public void postConstruct() {
        
        if(em.find(Admin.class, (long) 1) == null) {
            Admin a1 = new Admin();
            a1.setEmail("joestar@gmail.com");
            a1.setUsername("Joseph");
            a1.setPassword("caesar");
            adminSessionBean.createAdmin(a1);
            
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

}
