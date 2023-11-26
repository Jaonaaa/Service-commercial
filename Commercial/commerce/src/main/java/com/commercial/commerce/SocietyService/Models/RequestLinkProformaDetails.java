package com.commercial.commerce.SocietyService.Models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
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
@Entity(name = "request_link_proforma_details")
@Table(name = "request_link_proforma_details")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestLinkProformaDetails {

    @Id
    @GeneratedValue(generator = "request_link_proforma_details__seq")
    @SequenceGenerator(name = "request_link_proforma_details__seq", sequenceName = "_request_link_proforma_details_seq", allocationSize = 1)
    Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_request_link_proforma", unique = false)
    RequestLinkProforma requestLinkProforma;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_request_sc", unique = false)
    RequestSC requestSC;
}
