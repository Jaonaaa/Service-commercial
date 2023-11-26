package com.commercial.commerce.UserAuth.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.commercial.commerce.SocietyService.Models.Service;
import com.commercial.commerce.SocietyService.Service.ServiceService;
import com.commercial.commerce.Supplier.Models.Supplier;
import com.commercial.commerce.Supplier.Service.SupplierService;
import com.commercial.commerce.UserAuth.Auth.AuthenticationResponse;
import com.commercial.commerce.UserAuth.Auth.RefreshToken;
import com.commercial.commerce.UserAuth.Config.JwtService;
import com.commercial.commerce.UserAuth.Enum.Role;
import com.commercial.commerce.UserAuth.Enum.Society;
import com.commercial.commerce.UserAuth.Models.User;
import com.commercial.commerce.UserAuth.Repository.UserRepository;
import com.commercial.commerce.UserAuth.Request.AuthenticationRequest;
import com.commercial.commerce.UserAuth.Request.RefreshTokenRequest;
import com.commercial.commerce.UserAuth.Request.RegisterRequest;
import com.commercial.commerce.UserAuth.Response.RegisterData;
import com.commercial.commerce.Utils.Status;

import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class AuthService {

        private final UserRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final RefreshTokenService refreshTokenService;
        private final SupplierService supplierService;
        private final ServiceService serviceService;

        public AuthenticationResponse register(RegisterRequest request) {
                User user = User.builder().firstname(request.getFirstname()).lastname(request.getLastname())
                                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                                .society(getSociety(request.getSociety()))
                                .roles(getRole(request.getRole())).build();
                user = repository.save(user);
                return getAuthResponse(user, registerUserToSociety(user, request.getSociety_id()));
        }

        public Object getDetails_User(User user) {
                if (user.getSociety().equals(Society.SERVICE)) {
                        return serviceService.getUserSocietyDetails(user);
                } else if (user.getSociety().equals(Society.SUPPLIER)) {
                        return supplierService.getUserSocietyDetails(user);
                }
                return null;
        }

        public Object registerUserToSociety(User user, Long society_id) {
                if (user.getSociety().equals(Society.SERVICE)) {
                        Service service = serviceService.getServiceById(society_id);
                        return serviceService.saveServiceUser(service, user);

                } else if (user.getSociety().equals(Society.SUPPLIER)) {
                        Supplier supplier = supplierService.getSupplierById(society_id);
                        return supplierService.saveSupplierUser(supplier, user);
                }
                return null;
        }

        public Society getSociety(String societyS) {
                return Society.valueOf(societyS.toUpperCase());
        }

        public Role getRole(String roleS) {
                return Role.valueOf(roleS.toUpperCase());
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                User user = repository.findByEmail(request.getEmail()).orElseThrow();
                return getAuthResponse(user, getDetails_User(user));
        }

        public AuthenticationResponse getAuthResponse(User user, Object details) {
                String jwtToken = jwtService.generateToken(user);
                RefreshToken tokenRefresh = refreshTokenService.generateRefreshToken(user.getEmail());
                return AuthenticationResponse.builder().token(jwtToken).user(user)
                                .refresh_token(tokenRefresh.getToken()).details_user_(details)
                                .build();
        }

        public Status useRefreshToken(RefreshTokenRequest refreshTokenRequest) {
                User user = refreshTokenService.findByToken(refreshTokenRequest.getRefresh_token()).map(
                                refreshTokenService::verifyExpiration).map(RefreshToken::getUser).get();
                String accesToken = jwtService.generateToken(user);
                var tokenRefresh = refreshTokenService.generateRefreshToken(user.getEmail());

                return Status.builder()
                                .data(AuthenticationResponse.builder().token(accesToken).user(user)
                                                .refresh_token(tokenRefresh.getToken())
                                                .details_user_(getDetails_User(user))
                                                .build())
                                .status("good").details("token removed").build();
        }

        public RegisterData getRegisterData() {

                Role[] roles = Role.values();
                Society[] societies = Society.values();
                List<Supplier> supplierList = supplierService.getAll();
                List<Service> servicesList = serviceService.getAll();
                return RegisterData.builder().roles(roles).services(servicesList).society(societies)
                                .supplier(supplierList).build();
        }

        public Boolean chekcIfAlreadyExist(String email) {
                Boolean exist = false;
                Optional<User> user = repository.findByEmail(email);
                if (user.isPresent())
                        exist = true;

                return exist;
        }
}
