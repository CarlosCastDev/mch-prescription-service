package org.mch.rest.client;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.mch.dto.MedicalRecordDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.PathParam;
import java.util.Optional;

@Path("/patients")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "medical-records-api")
@RegisterClientHeaders(JwtPropagator.class)
public interface MedicalRecordsClient {

    @GET
    @Path("/{patientId}")
    Optional<MedicalRecordDTO> getMedicalRecord(@PathParam("patientId") Long patientId);
}

