package com.commercial.commerce.SocietyService.Models;

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
@Entity(name = "article_service")
@Table(name = "article_service")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ArticleService {

    @Id
    @GeneratedValue(generator = "article_service__seq")
    @SequenceGenerator(name = "article_service__seq", sequenceName = "_article_service_seq", allocationSize = 1)
    Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_service", unique = false)
    Service service;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_article", unique = false)
    Article article;

    @Column(name = "quantity", unique = false, nullable = false)
    Double quantity;

}
