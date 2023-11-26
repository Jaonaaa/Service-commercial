package com.commercial.commerce.SocietyService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.commerce.SocietyService.Models.ServiceUser;
import com.commercial.commerce.UserAuth.Models.User;

public interface ServiceUserRepo extends JpaRepository<ServiceUser, Long> {

    public ServiceUser findByUser(User user);
}
