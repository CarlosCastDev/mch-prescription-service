package org.mch.rest.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import org.mch.rest.security.TokenHolder;

@ApplicationScoped
@Slf4j
public class JwtPropagator implements ClientHeadersFactory {

    @Inject
    TokenHolder tokenHolder;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
                                                 MultivaluedMap<String, String> clientOutgoingHeaders) {
        String token = tokenHolder.getToken();

        log.info("EL TOKEN: {}", token);

        if (token != null && !token.isBlank()) {
            clientOutgoingHeaders.putSingle("Authorization", token);
        }

        return clientOutgoingHeaders;
    }
}