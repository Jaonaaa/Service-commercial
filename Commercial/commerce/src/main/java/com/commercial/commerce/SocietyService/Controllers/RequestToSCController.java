package com.commercial.commerce.SocietyService.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercial.commerce.SocietyService.Models.RequestSC;
import com.commercial.commerce.SocietyService.Models.RequestSCDetails;
import com.commercial.commerce.SocietyService.RequestStruct.RequestSCStruct;
import com.commercial.commerce.SocietyService.ResponseStruct.RequestSCStructResponse;
import com.commercial.commerce.SocietyService.Service.RequestSCService;
import com.commercial.commerce.Utils.Status;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/requestsc")
@RequiredArgsConstructor
public class RequestToSCController {

    private final RequestSCService requestSCService;

    @GetMapping
    public Status getAllRequestSC() {
        List<RequestSC> validate = requestSCService.getRequestSCValidate();
        List<RequestSC> invalidate = requestSCService.getRequestSCNotValidate();
        RequestSCStructResponse responseSC = RequestSCStructResponse.builder().validates(validate)
                .invalidates(invalidate).build();

        return Status.builder().status("good").data(responseSC).build();
    }

    @GetMapping("/{id}")
    public Status getRequestSCDetails(@PathVariable(name = "id") Long id_request) {

        List<RequestSCDetails> requests = requestSCService.getDetailsRequest(id_request);
        return Status.builder().status("good").data(requests).build();
    }

    @GetMapping("/v")
    public Status getAllRequestSCValidate() {
        return Status.builder().status("good").data(requestSCService.getRequestSCValidate()).build();
    }

    @GetMapping("/nv")
    public Status getAllRequestSCNotValidate() {
        return Status.builder().status("good").data(requestSCService.getRequestSCNotValidate()).build();
    }

    @GetMapping("/proforma")
    public Status getAllRequestProforma() {
        return Status.builder().status("good").data(requestSCService.getRequestProforma()).build();
    }

    @GetMapping("/supp/{id}/proforma")
    public Status getAllRequestProformaSupplier(@PathVariable(name = "id") Long id_supplier) {
        return Status.builder().status("good").data(requestSCService.getRequestProforma(id_supplier)).build();
    }

    @GetMapping("/proforma/{id}")
    public Status getAllProformaDetails(@PathVariable(name = "id") Long id_proforma) {
        return Status.builder().status("good").data(requestSCService.getRequestProformaDetails(id_proforma)).build();
    }

    @PostMapping
    public Status registerRequestSC(@RequestBody RequestSCStruct requestSC) {
        try {
            requestSCService.registerRequestSc(requestSC);
            return Status.builder().status("good").details("Request sent.").build();
        } catch (Exception e) {
            return Status.builder().status("error").details(e.getMessage()).build();
        }
    }

    @PostMapping("/v")
    public Status validateRequestSC(@RequestBody List<Long> id_requestSC) {
        try {
            requestSCService.validateRequest(id_requestSC);
            return Status.builder().status("good").details("Requests validate.").build();
        } catch (Exception e) {
            return Status.builder().status("error").details(e.getMessage()).build();
        }
    }

    @PostMapping("/proforma/{id}")
    public Status acceptProforma(@PathVariable(name = "id") Long id_proforma) {
        try {
            requestSCService.acceptToSendProforma(id_proforma);
            return Status.builder().status("good").details("Proforma sent.").build();
        } catch (Exception e) {
            return Status.builder().status("error").details(e.getMessage()).build();
        }
    }
}
