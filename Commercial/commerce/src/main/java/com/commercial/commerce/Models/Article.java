package com.commercial.commerce.Models;

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
@Entity(name = "article")
@Table(name = "article")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(generator = "article__seq")
    @SequenceGenerator(name = "article__seq", sequenceName = "_article_seq", allocationSize = 1)
    Long id;

    @Column(name = "name", unique = false, nullable = false)
    String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_categorie", unique = false)
    Categorie categorie;
}
