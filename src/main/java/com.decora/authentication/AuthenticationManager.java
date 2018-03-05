package com.decora.authentication;

import com.decora.persistence.database.MorphiaService;
import com.decora.persistence.database.dao.SystemUserDAO;
import com.decora.persistence.model.systemuser.SystemUser;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.security.auth.login.LoginException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;

@LocalBean
public class AuthenticationManager {

    @EJB
    private SystemUserDAO systemUserDAO;

    public AuthenticationManager(final MorphiaService service) {
        systemUserDAO = new SystemUserDAO(SystemUser.class, service.getDatastore());
    }

    public final SystemUser login(final String theEmail, final String password) throws LoginException {

        final List<SystemUser> systemUserList = systemUserDAO.filterByEmail(theEmail);

        if (systemUserList.size() == 0) {
            return null;
        } else {
            String decodedPwd = new String(Base64.getDecoder().decode(systemUserList.get(0).getPassword().getBytes(Charset.forName("UTF-8"))), Charset.forName("UTF-8"));
            if (decodedPwd.equals(password)) {
                return systemUserList.get(0);
            } else {
                throw new LoginException("Wrong password");
            }
        }
    }
}
