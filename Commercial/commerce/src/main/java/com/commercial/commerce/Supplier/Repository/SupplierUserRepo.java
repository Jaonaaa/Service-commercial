package com.commercial.commerce.Supplier.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.commerce.Supplier.Models.SupplierUser;
import com.commercial.commerce.UserAuth.Models.User;

public interface SupplierUserRepo extends JpaRepository<SupplierUser, Long> {

    SupplierUser findByUser(User user);
}
