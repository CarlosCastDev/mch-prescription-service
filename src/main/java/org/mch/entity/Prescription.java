package org.mch.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "prescriptions")
public class Prescription extends PanacheEntity {

    // Identificador del paciente al que se le emite la receta
    public Long patientId;

    // Identificador del médico que emite la receta
    public Long doctorId;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<PrescriptionItem> items;

    // Fecha y hora en que se emite la receta
    public LocalDateTime prescriptionDate;

    // Detalles de los medicamentos recetados (puede ser un JSON o texto estructurado)
    @Column(length = 1000)
    public String medicationDetails;

    // Instrucciones de dosificación o administración
    @Column(length = 500)
    public String dosageInstructions;

    // Notas adicionales (opcional)
    @Column(length = 1000)
    public String notes;
}
