package com.commercial.commerce.Supplier.Models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.commercial.commerce.UserAuth.Models.User;

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
@Entity(name = "supplier_user")
@Table(name = "supplier_user")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SupplierUser {

    @Id
    @GeneratedValue(generator = "supplier_user__seq")
    @SequenceGenerator(name = "supplier_user__seq", sequenceName = "_supplier_user_seq", allocationSize = 1)
    Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_user", unique = false)
    User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_supplier", unique = false)
    Supplier supplier;
}
