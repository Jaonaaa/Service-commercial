package com.commercial.commerce.Models;

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
@Entity(name = "localisation")
@Table(name = "localisation")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Localisation {

    @Id
    @GeneratedValue(generator = "loc__seq")
    @SequenceGenerator(name = "loc__seq", sequenceName = "_loc_seq", allocationSize = 1)
    Long id;

    @Column(name = "name", unique = true, nullable = false)
    String name;
}
