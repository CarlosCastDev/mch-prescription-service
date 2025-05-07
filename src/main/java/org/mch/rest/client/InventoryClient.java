package org.mch.rest.client;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.mch.dto.MedicalRecordDTO;

import java.util.Optional;

@Path("/inventory")
@RegisterRestClient(configKey = "inventory-api")
@RegisterProvider(MyExceptionMapper.class)
@RegisterClientHeaders(JwtPropagator.class)
public interface InventoryClient {

    @GET
    @Path("/check-stock/{medicineId}/{quantity}")
    @Produces(MediaType.APPLICATION_JSON)
    Response isStockAvailable(@PathParam("medicineId") Long medicineId, @PathParam("quantity") int quantity);
}