package com.commercial.commerce.SocietyService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.commerce.SocietyService.Models.Service;

public interface ServiceRepo extends JpaRepository<Service, Long> {

    // @Query("SELECT ar FROM movement ar WHERE ar.article.id = :ida ORDER BY date
    // DESC")
    // List<Movement> findAllByIdArticle(@Param("ida") long id_article);

    Service findFirstByLogo(String commercialLogo);

}
