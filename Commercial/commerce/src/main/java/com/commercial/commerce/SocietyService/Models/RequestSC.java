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
@Entity(name = "request_sc")
@Table(name = "request_sc")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestSC {

    @Id
    @GeneratedValue(generator = "request_sc__seq")
    @SequenceGenerator(name = "request_sc__seq", sequenceName = "_request_sc_seq", allocationSize = 1)
    Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_service_commercial", unique = false)
    Service serviceCommercial;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_service_sender", unique = false)
    Service serviceSender;

    @Column(name = "date", nullable = false, updatable = false)
    Timestamp date;

    @Column(name = "validate", nullable = false)
    Boolean validate;

}
