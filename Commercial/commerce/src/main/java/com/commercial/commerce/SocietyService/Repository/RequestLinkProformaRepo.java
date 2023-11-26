package com.commercial.commerce.SocietyService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.commerce.SocietyService.Models.RequestLinkProforma;

public interface RequestLinkProformaRepo extends JpaRepository<RequestLinkProforma, Long> {

    List<RequestLinkProforma> findByValidation(Integer validation_level);

}
