package com.commercial.commerce.Supplier.Models;

import java.sql.Timestamp;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.commercial.commerce.SocietyService.Models.RequestProforma;
import com.commercial.commerce.SocietyService.Models.Service;

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
@Entity(name = "request_proforma_supplier")
@Table(name = "request_proforma_supplier")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestProformaSupplier {

    @Id
    @GeneratedValue(generator = "request_proforma_supplier__seq")
    @SequenceGenerator(name = "request_proforma_supplier__seq", sequenceName = "_request_proforma_supplier_seq", allocationSize = 1)
    Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_request_proforma", unique = false)
    RequestProforma requestProforma;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_service", unique = false)
    Service service;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_supplier", unique = false)
    Supplier supplier;

    @Column(name = "answered", nullable = false)
    Boolean answered;
}
