package com.commercial.commerce.UserAuth.Response;

import java.util.List;

import com.commercial.commerce.SocietyService.Models.Service;
import com.commercial.commerce.Supplier.Models.Supplier;
import com.commercial.commerce.UserAuth.Enum.Role;
import com.commercial.commerce.UserAuth.Enum.Society;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RegisterData {

    Society[] society;
    Role[] roles;
    List<Service> services;
    List<Supplier> supplier;

}
