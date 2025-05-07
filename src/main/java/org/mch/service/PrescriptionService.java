package org.mch.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mch.rest.client.InventoryClient;

@ApplicationScoped
@Slf4j
public class PrescriptionService {

    @Inject
    @RestClient
    InventoryClient inventoryClient;

    public boolean validateStock(Long medicineId, int quantity) throws Exception {
        try (Response response = inventoryClient.isStockAvailable(medicineId, quantity)){
            return response.getStatus() == Response.Status.OK.getStatusCode();
        } catch (Exception e) {
            log.error("Error al validar Stock", e);
            throw new Exception("Error al validar Stock"); // Manejo de error
        }
    }
}
