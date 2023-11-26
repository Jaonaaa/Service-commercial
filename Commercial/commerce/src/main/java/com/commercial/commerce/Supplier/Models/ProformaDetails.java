package com.commercial.commerce.Supplier.Models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "proforma_details")
@Table(name = "proforma_details")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProformaDetails {

    @Id
    @GeneratedValue(generator = "proforma_details__seq")
    @SequenceGenerator(name = "proforma_details__seq", sequenceName = "_proforma_details_seq", allocationSize = 1)
    Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_request_proforma_supplier", unique = false)
    RequestProformaSupplier requestProformaSupplier;

    @Column(name = "pdf_path", unique = false)
    String pdfPath;
}
