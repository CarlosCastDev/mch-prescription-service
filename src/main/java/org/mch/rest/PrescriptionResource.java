package org.mch.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mch.dto.MedicalRecordDTO;
import org.mch.entity.Prescription;
import org.mch.entity.PrescriptionItem;
import org.mch.rest.client.MedicalRecordsClient;
import org.mch.service.PrescriptionService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Path("/prescriptions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PrescriptionResource {

    @GET
    public List<Prescription> getAllPrescriptions() {
        return Prescription.listAll();
    }

    @Inject
    @RestClient
    MedicalRecordsClient medicalRecordsClient;

    @Inject
    PrescriptionService prescriptionService;

    @GET
    @Path("/{id}")
    public Prescription getPrescription(@PathParam("id") Long id) {
        Prescription prescription = Prescription.findById(id);
        if (prescription == null) {
            throw new NotFoundException("Receta no encontrada");
        }
        return prescription;
    }

    @POST
    @Transactional
    public Response createPrescription(Prescription prescription, @Context UriInfo uriInfo) throws Exception {

        for (PrescriptionItem item : prescription.items) {
            if (!prescriptionService.validateStock(item.getMedicineId(), item.getQuantity())) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                        .entity("Stock insuficiente para el medicamento con ID " + item.getMedicineId())
                        .build());
            }
        }

        Optional<MedicalRecordDTO> medicalRecord = medicalRecordsClient.getMedicalRecord(prescription.patientId);

        if (medicalRecord.isEmpty()) {
            throw new IllegalStateException("No se encontró historial médico para el paciente " +prescription.patientId);
        }

        log.info("diagnostico: {}", medicalRecord.get().getDiagnosis());

        prescription.notes = medicalRecord.get().getDiagnosis();

        // Asignar la relación inversa en cada PrescriptionItem
        if (prescription.items != null) {
            prescription.items.forEach(item -> item.setPrescription(prescription));
        }

        prescription.persist();
        if (prescription.isPersistent()) {
            UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(prescription.id.toString());
            return Response.created(builder.build()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Prescription updatePrescription(@PathParam("id") Long id, Prescription updatedPrescription) {
        Prescription prescription = Prescription.findById(id);
        if (prescription == null) {
            throw new NotFoundException("Receta no encontrada");
        }
        prescription.patientId = updatedPrescription.patientId;
        prescription.doctorId = updatedPrescription.doctorId;
        prescription.prescriptionDate = updatedPrescription.prescriptionDate;
        prescription.medicationDetails = updatedPrescription.medicationDetails;
        prescription.dosageInstructions = updatedPrescription.dosageInstructions;
        prescription.notes = updatedPrescription.notes;
        return prescription;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletePrescription(@PathParam("id") Long id) {
        Prescription prescription = Prescription.findById(id);
        if (prescription == null) {
            throw new NotFoundException("Receta no encontrada");
        }
        prescription.delete();
        return Response.noContent().build();
    }
}

