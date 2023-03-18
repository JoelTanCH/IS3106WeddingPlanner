/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Admin;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wangp
 */
@Local
public interface AdminSessionBeanLocal {

    public void deleteAdmin(Long adminId);

    public void createAdmin(Admin admin);

    public List<Admin> getAdminsByUsernamePassword(String email, String password);

    public List<Admin> searchAdminsByUsernameEmail(String username, String email);
    
}
