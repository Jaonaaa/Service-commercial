package com.commercial.commerce.Supplier.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.commerce.SocietyService.Models.RequestProforma;
import com.commercial.commerce.Supplier.Models.RequestProformaSupplier;
import com.commercial.commerce.Supplier.Models.Supplier;

public interface RequestProformaSupplierRepo extends JpaRepository<RequestProformaSupplier, Long> {

    List<RequestProformaSupplier> findBySupplier(Supplier supplier);

    List<RequestProformaSupplier> findByRequestProforma(RequestProforma requestProforma);

    List<RequestProformaSupplier> findAllByOrderByIdAsc();

}
