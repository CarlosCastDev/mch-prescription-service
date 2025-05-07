package org.mch.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PrescriptionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long medicineId;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    public PrescriptionItem() {}

    public PrescriptionItem(Long medicineId, int quantity, Prescription prescription) {
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.prescription = prescription;
    }

    // Getters y Setters
}

