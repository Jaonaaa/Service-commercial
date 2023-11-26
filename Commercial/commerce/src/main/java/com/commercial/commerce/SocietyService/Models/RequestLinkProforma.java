package com.commercial.commerce.SocietyService.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "request_link_proforma")
@Table(name = "request_link_proforma")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestLinkProforma {

    @Id
    @GeneratedValue(generator = "request_link_proforma__seq")
    @SequenceGenerator(name = "request_link_proforma__seq", sequenceName = "_request_link_proforma_seq", allocationSize = 1)
    Long id;

    @Column(name = "validation", unique = false, nullable = true)
    Integer validation;

}
