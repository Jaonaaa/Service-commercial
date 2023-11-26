package com.commercial.commerce.Supplier.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercial.commerce.Supplier.Models.Supplier;
import com.commercial.commerce.Supplier.Models.SupplierUser;
import com.commercial.commerce.Supplier.Repository.SupplierRepo;
import com.commercial.commerce.Supplier.Repository.SupplierUserRepo;
import com.commercial.commerce.UserAuth.Models.User;

@Service
public class SupplierService {

    @Autowired
    SupplierRepo supplierRepository;
    @Autowired
    SupplierUserRepo supplierUserRepository;

    public SupplierUser getUserSocietyDetails(User user) {
        return supplierUserRepository.findByUser(user);
    }

    public Supplier getSupplierById(Long id_supplier) {
        return supplierRepository.findById(id_supplier).get();
    }

    public SupplierUser saveSupplierUser(Supplier supp, User user) {
        SupplierUser sup = SupplierUser.builder().supplier(supp).user(user).build();
        return supplierUserRepository.save(sup);
    }

    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

}
