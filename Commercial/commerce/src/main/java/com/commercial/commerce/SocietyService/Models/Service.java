package com.commercial.commerce.SocietyService.Models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.commercial.commerce.Models.Localisation;

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
@Entity(name = "service")
@Table(name = "service")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Service {

    @Id
    @GeneratedValue(generator = "service__seq")
    @SequenceGenerator(name = "service__seq", sequenceName = "_service_seq", allocationSize = 1)
    Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "logo", nullable = false)
    String logo;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_localisation", unique = false)
    Localisation localisation;

    @Column(name = "color", nullable = false)
    String color;
}
