package com.commercial.commerce.Supplier.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.commerce.Supplier.Models.Supplier;

public interface SupplierRepo extends JpaRepository<Supplier, Long> {

}
