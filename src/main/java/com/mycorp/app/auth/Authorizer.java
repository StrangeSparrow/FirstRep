package com.mycorp.app.auth;

import com.mycorp.app.Constants;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Set;

public class Authorizer implements SecurityContext {
    private final Set<String> roles;
    boolean isSecure;
    Principal principal;

    public Authorizer(Set<String> roles, Principal principal, boolean isSecure) {
        this.roles = roles;
        this.principal = principal;
        this.isSecure = isSecure;
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return roles.contains(role);
    }

    @Override
    public boolean isSecure() {
        return isSecure;
    }

    @Override
    public String getAuthenticationScheme() {
        return Constants.AUTHENTICATION_SCHEME;
    }
}
