package com.commercial.commerce.SocietyService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;

import com.commercial.commerce.SocietyService.Models.RequestSC;

public interface RequestSCRepo extends JpaRepository<RequestSC, Long> {

    List<RequestSC> findByValidateOrderByDateDesc(Boolean validate);

    // @Query("SELECT ar FROM movement ar WHERE ar.article.id = :ida ORDER BY date
    // DESC")
    // List<RequestSC> findByValidate(@Param("ida") long id_article);

}
