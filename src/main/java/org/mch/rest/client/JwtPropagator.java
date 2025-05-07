package org.mch.rest.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import org.mch.rest.security.TokenHolder;

@ApplicationScoped
public class JwtPropagator implements ClientHeadersFactory {

    @Inject
    TokenHolder tokenHolder;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
                                                 MultivaluedMap<String, String> clientOutgoingHeaders) {
        String token = tokenHolder.getToken();

        if (token != null && !token.isBlank()) {
            clientOutgoingHeaders.putSingle("Authorization", token);
        }

        return clientOutgoingHeaders;
    }
}