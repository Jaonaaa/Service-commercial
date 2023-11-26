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
@Entity(name = "categorie")
@Table(name = "categorie")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Categorie {

    @Id
    @GeneratedValue(generator = "categorie__seq")
    @SequenceGenerator(name = "categorie__seq", sequenceName = "_categorie_seq", allocationSize = 1)
    Long id;

    @Column(name = "name", unique = false, nullable = false)
    String name;
}
