package com.commercial.commerce.SocietyService.Service;

import java.util.List;
import java.util.Vector;

import com.commercial.commerce.SocietyService.Models.RequestLinkProforma;
import com.commercial.commerce.SocietyService.Models.RequestProforma;
import com.commercial.commerce.SocietyService.Repository.RequestLinkProformaRepo;
import com.commercial.commerce.SocietyService.Repository.RequestProformaRepo;

import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class RequestLinkProformaService {

    private final RequestLinkProformaRepo requestLinkProformaRepo;
    private final RequestProformaRepo requestProformaRepo;

    // 0 no validation
    // 1 asking validation to finance
    // 2 validate for finance
    // -1 reported
    public void askValidationToFinance(Long id_link) {
        RequestLinkProforma req = requestLinkProformaRepo.findById(id_link).get();
        req.setValidation(1);
        requestLinkProformaRepo.save(req);
    }

    public List<RequestProforma> getAllValidationRequest() {
        List<RequestLinkProforma> requests = requestLinkProformaRepo.findByValidation(1);
        List<RequestProforma> links = new Vector<>();
        for (RequestLinkProforma req : requests) {
            RequestProforma proforma = requestProformaRepo.findByRequestLinkProforma(req);
            links.add(proforma);
        }
        return links;
    }

    public void validationFromFinance(Long id_link) {
        RequestLinkProforma req = requestLinkProformaRepo.findById(id_link).get();
        // req.setValidation(2);
        // requestLinkProformaRepo.save(req);
        // do some stuff for the transaction
        // Etooo
        // mila oe alefa any am supplier amzay le demande tena izy de veo zareo mandefa
        // mifanaraka aminy
        // MILA ampesaina le
        // public List<List<PurchaseSupplierArticle>> getPurchaseOrder(Long
        // request_link_proforma) { => azahoana details tsy manadala
        // anleh nangatahana < mihena stock zareo

    }

    public void reportedValidationToFinance(Long id_link) {
        RequestLinkProforma req = requestLinkProformaRepo.findById(id_link).get();
        req.setValidation(-1);
        requestLinkProformaRepo.save(req);

    }

}
