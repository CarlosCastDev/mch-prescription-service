package org.mch.rest.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.inject.Inject;
import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class InboundTokenFilter implements ContainerRequestFilter {

    @Inject
    TokenHolder tokenHolder;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Extrae el valor del header "Authorization"
        String authHeader = requestContext.getHeaderString("Authorization");
        if (authHeader != null && !authHeader.isBlank()) {
            tokenHolder.setToken(authHeader);
        }
    }
}
