package com.commercial.commerce.SocietyService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.commerce.SocietyService.Models.RequestSC;
import com.commercial.commerce.SocietyService.Models.RequestSCDetails;

public interface RequestSCDetailsRepo extends JpaRepository<RequestSCDetails, Long> {

    List<RequestSCDetails> findByRequestsc(RequestSC requestSC);

}
