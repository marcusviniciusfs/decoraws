package com.decora.http.resource.systemuser;

import com.decora.http.ResourcePath;
import com.decora.http.resource.Permissions;
import com.decora.persistence.database.MorphiaService;
import com.decora.persistence.database.dao.SystemUserDAO;
import com.decora.persistence.model.systemuser.SystemUser;
import com.mongodb.WriteResult;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;

@LocalBean
@RolesAllowed(Permissions.ADMIN)
@Path(ResourcePath.SERVICE_SYSTEM_USER)
public class SystemUserService {

    @EJB
    private SystemUserDAO systemUserDAO;

    public SystemUserService(final MorphiaService service) {
        service.getDatastore().ensureIndexes();
        systemUserDAO = new SystemUserDAO(SystemUser.class, service.getDatastore());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public final Response create(@Valid @NotNull final SystemUser systemUser) {
        try {
            if (systemUser.getEmail() != null && !systemUser.getEmail().isEmpty()
                    && systemUser.getPassword() != null && !systemUser.getPassword().isEmpty()) {

                byte[] password = systemUser.getPassword().getBytes(Charset.forName("UTF-8"));
                systemUser.setPassword(Base64.getEncoder().encodeToString(password));
                SystemUser newSystemUser = systemUserDAO.create(systemUser);
                return Response.status(Response.Status.OK).entity(newSystemUser).build();

            } else {
                return Response.status(Response.Status.CONFLICT).entity("Username or password can not be null").build();
            }
        } catch (WebApplicationException e) {
            final Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new WebApplicationException(rootCause, Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(rootCause.getMessage()).build());
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public final Response update(@Valid @NotNull final SystemUser updateSystemUser) {
        if (updateSystemUser.getEmail() == null || updateSystemUser.getEmail().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid object to update").build();
        }

        List<SystemUser> readSystemUser = systemUserDAO.filterByEmail(updateSystemUser.getEmail());
        if (readSystemUser.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND).entity("Invalid object to update").build();
        }

        updateSystemUser(readSystemUser.get(0), updateSystemUser);
        SystemUser systemUser = systemUserDAO.create(readSystemUser.get(0));
        return Response.status(Response.Status.OK).entity(systemUser).build();
    }

    @DELETE
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response delete(@PathParam("email") final String theEmail) {
        final WriteResult removedSystemUser;
        List<SystemUser> systemUserList = systemUserDAO.filterByEmail(theEmail);

        if (systemUserList.size() > 0) {
            removedSystemUser = systemUserDAO.delete(systemUserList.get(0));
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("There is no user to remove").build();
        }
        return Response.status(Response.Status.OK).entity(removedSystemUser).build();
    }

    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response retrieve(@PathParam("email") final String theEmail) {
        final List<SystemUser> retrievedSystemUser = systemUserDAO.filterByEmail(theEmail);
        if (retrievedSystemUser.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
        return Response.status(Response.Status.OK).entity(retrievedSystemUser.get(0)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public final List<SystemUser> retrieveList() {
        List<SystemUser> retrievedSystemUserList = systemUserDAO.retrieveAll();
        return retrievedSystemUserList;
    }

    private void updateSystemUser(final SystemUser oringSystemUser, final SystemUser newSystemUser) {
        //update SystemUser data
        oringSystemUser.setName(newSystemUser.getName());
        oringSystemUser.setPhone(newSystemUser.getPhone());

        //update password
        if (newSystemUser.getPassword() != null && !newSystemUser.getPassword().isEmpty()) {
            byte[] password = newSystemUser.getPassword().getBytes(Charset.forName("UTF-8"));
            oringSystemUser.setPassword(Base64.getEncoder().encodeToString(password));
        }

        //update address data
        oringSystemUser.getAddress().setState(newSystemUser.getAddress().getState());
        oringSystemUser.getAddress().setTown(newSystemUser.getAddress().getTown());
        oringSystemUser.getAddress().setStreet(newSystemUser.getAddress().getStreet());
        oringSystemUser.getAddress().setNumber(newSystemUser.getAddress().getNumber());
        oringSystemUser.getAddress().setPostcode(newSystemUser.getAddress().getPostcode());
    }
}
