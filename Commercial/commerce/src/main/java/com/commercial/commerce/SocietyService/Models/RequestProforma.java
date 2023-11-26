package com.commercial.commerce.SocietyService.Models;

import java.sql.Timestamp;

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
@Entity(name = "request_proforma")
@Table(name = "request_proforma")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestProforma {

    @Id
    @GeneratedValue(generator = "request_proforma__seq")
    @SequenceGenerator(name = "request_proforma__seq", sequenceName = "_request_proforma_seq", allocationSize = 1)
    Long id;

    @Column(name = "date", nullable = false, updatable = false)
    Timestamp date;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_request_link_proforma", unique = false)
    RequestLinkProforma requestLinkProforma;
}
