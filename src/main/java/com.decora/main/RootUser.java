
package com.decora.main;

import com.decora.persistence.database.MorphiaService;
import com.decora.persistence.database.dao.SystemUserDAO;
import com.decora.persistence.model.address.Address;
import com.decora.persistence.model.systemuser.SystemUser;

public class RootUser {

    public RootUser() {
    }

    public final void createRootUser(final MorphiaService service) {

        SystemUserDAO systemUserDAO = new SystemUserDAO(SystemUser.class, service.getDatastore());

        if (systemUserDAO.filterByEmail("root@decora.com").size() == 0) {
            Address address = new Address();
            address.setNumber("99");
            address.setStreet("Mongo Street");
            address.setTown("Mongo Town");
            address.setState("Mongo State");
            address.setPostcode("zipcode");

            SystemUser systemUser = new SystemUser();
            systemUser.setAddress(address);
            systemUser.setName("Root User");
            systemUser.setPassword("cGFzc3dvcmQ=");
            systemUser.setPhone("99999999");
            systemUser.setEmail("root@decora.com");

            systemUserDAO.save(systemUser);
        }
    }
}
