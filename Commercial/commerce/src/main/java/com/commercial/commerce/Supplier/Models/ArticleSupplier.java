package com.commercial.commerce.Supplier.Models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.commercial.commerce.Models.Article;

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
@Entity(name = "article_supplier")
@Table(name = "article_supplier")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ArticleSupplier {

    @Id
    @GeneratedValue(generator = "article_sup__seq")
    @SequenceGenerator(name = "article_sup__seq", sequenceName = "_article_sup_seq", allocationSize = 1)
    Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_supplier", unique = false)
    Supplier supplier;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_article", unique = false)
    Article article;

    @Column(name = "price_HT", unique = false, nullable = false)
    Double price_HT;
    @Column(name = "tva", unique = false, nullable = false)
    Double tva;
    @Column(name = "quantity", unique = false, nullable = false)
    Double quantity;
}
