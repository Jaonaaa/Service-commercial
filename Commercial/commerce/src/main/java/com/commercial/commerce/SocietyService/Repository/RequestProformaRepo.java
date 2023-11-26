package com.commercial.commerce.SocietyService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.commerce.SocietyService.Models.RequestLinkProforma;
import com.commercial.commerce.SocietyService.Models.RequestProforma;

public interface RequestProformaRepo extends JpaRepository<RequestProforma, Long> {

    RequestProforma findByRequestLinkProforma(RequestLinkProforma requestLink);

}
