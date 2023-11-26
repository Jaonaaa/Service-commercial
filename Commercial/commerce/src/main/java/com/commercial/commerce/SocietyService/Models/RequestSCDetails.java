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
@Entity(name = "request_sc_details")
@Table(name = "request_sc_details")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestSCDetails {

    @Id
    @GeneratedValue(generator = "request_sc_details__seq")
    @SequenceGenerator(name = "request_sc_details__seq", sequenceName = "_request_sc_details_seq", allocationSize = 1)
    Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_request_sc", unique = false)
    RequestSC requestsc;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_article", unique = false)
    Article article;

    @Column(name = "quantity", nullable = false)
    Double quantity;

}
