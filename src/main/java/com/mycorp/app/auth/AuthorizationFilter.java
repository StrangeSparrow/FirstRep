package com.mycorp.app.auth;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.AccessDeniedException;
import java.security.Principal;

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        Method method = resourceInfo.getResourceMethod();

        if (method.isAnnotationPresent(DenyAll.class)) {
            refuseRequest();
        }

        RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
        if (rolesAllowed != null) {
            performAuthorization(rolesAllowed.value(), requestContext);
            return;
        }

        if (method.isAnnotationPresent(PermitAll.class)) {
            return;
        }

        rolesAllowed = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);

        if (rolesAllowed != null) {
            performAuthorization(rolesAllowed.value(), requestContext);
        }

        if (resourceInfo.getResourceClass().isAnnotationPresent(PermitAll.class)) {
            return;
        }

        if (!isAuthenticated(requestContext)) {
            refuseRequest();
        }
    }

    private void performAuthorization(String[] rolesAllowed, ContainerRequestContext requestContext) throws AccessDeniedException {
        if (rolesAllowed.length > 0 && !isAuthenticated(requestContext)) {
            refuseRequest();
        }

        for (final String role : rolesAllowed) {
            if (requestContext.getSecurityContext().isUserInRole(role)) {
                return;
            }
        }
        refuseRequest();
    }

    private boolean isAuthenticated(final ContainerRequestContext requestContext) {
        Principal principal = requestContext.getSecurityContext().getUserPrincipal();
        return principal != null;
    }

    private void refuseRequest() throws AccessDeniedException {
        throw new AccessDeniedException("You don't have permissions to perform this action.");
    }
}

