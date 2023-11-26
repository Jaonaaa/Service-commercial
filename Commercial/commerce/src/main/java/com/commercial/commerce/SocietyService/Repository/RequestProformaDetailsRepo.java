package com.commercial.commerce.SocietyService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.commerce.SocietyService.Models.RequestProforma;
import com.commercial.commerce.SocietyService.Models.RequestProformaDetails;

public interface RequestProformaDetailsRepo extends JpaRepository<RequestProformaDetails, Long> {

    List<RequestProformaDetails> findByRequestProforma(RequestProforma requestP);

}
