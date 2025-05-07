package org.mch.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicalRecordDTO {
    // Referencia al paciente (para simplificar, se guarda el ID del paciente)
    public Long patientId;

    // Identificador del médico que atendió o creó el registro
    public Long doctorId;

    // Fecha y hora del registro o consulta
    public LocalDateTime recordDate;

    // Diagnóstico realizado
    public String diagnosis;

    // Tratamiento prescrito
    public String treatment;

    // Notas adicionales
    public String notes;
}
