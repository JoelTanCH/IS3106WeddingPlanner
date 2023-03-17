/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Admin;
import entity.Guest;
import entity.GuestTable;
import entity.WeddingProject;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wangp
 */
@Stateless
public class AdminSessionBean implements AdminSessionBeanLocal {

    @PersistenceContext(unitName = "IS3106WeddingPlanner-ejbPU")
    private EntityManager em;

    @Override
    public void createAdmin(Admin admin) {
        em.persist(admin);
    }

    @Override
    public void deleteAdmin(Long adminId) { // technically throws an unchecked exception NoResultFound from em.find
        // but im too lazy to catch
        Admin admin = em.find(Admin.class, adminId);
        em.remove(admin);
    }

    public List<Admin> getAdminsByUsernamePassword(String email, String password) {
        Query q;
        q = em.createQuery("SELECT a FROM Admin a WHERE a.email LIKE :email AND a.password LIKE :password");
        q.setParameter("email", email);
        q.setParameter("password", password);
        // test and see if it works with empty strings
        return q.getResultList();
    }

    public List<Admin> searchAdminsByUsernameEmail(String username, String email) {
        Query q;
        q = em.createQuery("SELECT a FROM Admin a WHERE a.username LIKE :username AND a.email LIKE :email");
        q.setParameter("username", "%" + username + "%");
        q.setParameter("email", "%" + email + "%");
        // test and see if it works with empty strings
        return q.getResultList();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
