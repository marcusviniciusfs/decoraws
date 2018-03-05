package com.decora.http.resource.authentication;

import com.decora.authentication.AuthenticationManager;
import com.decora.http.ResourcePath;
import com.decora.persistence.database.MorphiaService;
import com.decora.persistence.model.systemuser.SystemUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.ejb.LocalBean;
import javax.security.auth.login.LoginException;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.StringTokenizer;

@PermitAll
@LocalBean
@Path(ResourcePath.ROOT)
public class AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);
    private final AuthenticationManager authentication;

    public AuthenticationService(final MorphiaService service) {
        authentication = new AuthenticationManager(service);
    }

    @GET
    @Path(ResourcePath.LOGIN)
    @Produces(MediaType.APPLICATION_JSON)
    public final Response login(final @HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        String authorization = authorizationHeader.replace("Basic ", "");
        authorization = new String(Base64.getDecoder().decode(authorization.getBytes(Charset.forName("UTF-8"))), Charset.forName("UTF-8"));
        StringTokenizer stringTokenizer = new StringTokenizer(authorization, ":");
        String email = stringTokenizer.nextToken();
        String password = stringTokenizer.nextToken();

        try {
            SystemUser systemUser = authentication.login(email, password);
            if (systemUser != null) {
                String basicAuthorization = email + ":" + password;
                byte[] authorizationBytes = basicAuthorization.getBytes(Charset.forName("UTF-8"));
                basicAuthorization = "Basic " + Base64.getEncoder().encodeToString(authorizationBytes);
                LOGGER.debug("/login response: 200 - OK");
                return Response.ok().header("Authorization", basicAuthorization).entity(systemUser).build();
            } else {
                LOGGER.debug("/login response: 401 - Unauthorized");
                return Response.status(Response.Status.UNAUTHORIZED).entity("System user does not exist").build();
            }
        } catch (LoginException e) {
            LOGGER.error("Server error:", e);
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid username or password.").build();
        }
    }
}
