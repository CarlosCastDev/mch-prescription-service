package org.mch.rest.client;

import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Slf4j
public class MyExceptionMapper implements ResponseExceptionMapper<RuntimeException> {
    @Override
    public RuntimeException toThrowable(Response response) {
        StringBuilder sb = new StringBuilder();
        sb.append("Error en RestClient\n");
        sb.append("Status: ").append(response.getStatus()).append("\n");
        sb.append("MediaType: ").append(response.getMediaType()).append("\n");
        sb.append("Headers: ").append(response.getHeaders()).append("\n");

        if (response.hasEntity()) {
            try {
                String body = response.readEntity(String.class);
                sb.append("Body: ").append(body).append("\n");
            } catch (Exception e) {
                sb.append("Body: <no se pudo leer: ").append(e.getMessage()).append(">\n");
            }
        } else {
            sb.append("Body: <sin contenido>\n");
        }

        System.out.println(sb.toString());
        return new RuntimeException("Error invocando servicio remoto. Ver logs.");
    }
}