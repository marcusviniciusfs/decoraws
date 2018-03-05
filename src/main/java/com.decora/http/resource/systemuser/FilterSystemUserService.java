package com.decora.http.resource.systemuser;

import com.decora.http.ResourcePath;
import com.decora.http.resource.Permissions;
import com.decora.persistence.database.MorphiaService;
import com.decora.persistence.database.dao.SystemUserDAO;
import com.decora.persistence.model.systemuser.SystemUser;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RolesAllowed({Permissions.USER, Permissions.ADMIN})
@LocalBean
@Path(ResourcePath.SERVICE_SYSTEM_USER)
public class FilterSystemUserService {

    @EJB
    private SystemUserDAO systemUserDAO;

    public FilterSystemUserService(final MorphiaService service) {
        systemUserDAO = new SystemUserDAO(SystemUser.class, service.getDatastore());
    }

    @GET
    @Path(ResourcePath.FIND_BY)
    @Produces(MediaType.APPLICATION_JSON)
    public final Response findBy(@QueryParam("name") final String theName, @QueryParam("email") final String theEmail,
                                 @QueryParam("phone") final String thePhone, @QueryParam("town") final String theTown,
                                 @QueryParam("state") final String theState, @QueryParam("orderBy") final String orderBY,
                                 @QueryParam("skip") final String theSkip, @QueryParam("limit") final String theLimit) {

        List<SystemUser> systemUserList = systemUserDAO.filterSystemUser(theName, theEmail, thePhone, theTown,
                theState, orderBY, theSkip, theLimit);

        if (systemUserList.isEmpty()) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
        }
        return Response.status(Response.Status.OK).entity(systemUserList).build();
    }
}
