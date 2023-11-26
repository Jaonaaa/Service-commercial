package com.commercial.commerce.SocietyService.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.commercial.commerce.SocietyService.Models.Service;
import com.commercial.commerce.SocietyService.Models.ServiceUser;
import com.commercial.commerce.SocietyService.Repository.ServiceRepo;
import com.commercial.commerce.SocietyService.Repository.ServiceUserRepo;
import com.commercial.commerce.UserAuth.Models.User;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    ServiceUserRepo serviceUserRepo;
    @Autowired
    ServiceRepo serviceRepo;

    public ServiceUser getUserSocietyDetails(User user) {
        return serviceUserRepo.findByUser(user);
    }

    public Service getServiceById(Long id_service) {
        return serviceRepo.findById(id_service).get();
    }

    public ServiceUser saveServiceUser(Service service, User user) {
        ServiceUser sup = ServiceUser.builder().service(service).user(user).build();
        return serviceUserRepo.save(sup);
    }

    public List<Service> getAll() {
        return serviceRepo.findAll();
    }
}
