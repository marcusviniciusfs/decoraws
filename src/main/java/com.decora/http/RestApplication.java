package com.decora.http;

import com.decora.http.resource.authentication.AuthenticationService;
import com.decora.http.resource.filter.Filter;
import com.decora.http.resource.systemuser.FilterSystemUserService;
import com.decora.http.resource.systemuser.SystemUserService;
import com.decora.persistence.database.MorphiaService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class RestApplication extends Application {

    private final MorphiaService service = new MorphiaService();

    @Override
    public final Set<Object> getSingletons() {
        final Set<Object> set = new HashSet<>();

        set.add(new SystemUserService(service));
        set.add(new AuthenticationService(service));
        set.add(new FilterSystemUserService(service));
        set.add(new Filter(service));

        return set;
    }
}
