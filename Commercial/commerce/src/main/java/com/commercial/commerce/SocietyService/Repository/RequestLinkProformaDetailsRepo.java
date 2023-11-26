package com.commercial.commerce.SocietyService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.commerce.SocietyService.Models.RequestLinkProforma;
import com.commercial.commerce.SocietyService.Models.RequestLinkProformaDetails;

public interface RequestLinkProformaDetailsRepo extends JpaRepository<RequestLinkProformaDetails, Long> {

    List<RequestLinkProformaDetails> findByRequestLinkProforma(RequestLinkProforma requestLink);

}
