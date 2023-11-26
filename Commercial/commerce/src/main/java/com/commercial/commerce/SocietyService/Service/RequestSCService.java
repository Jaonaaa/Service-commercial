package com.commercial.commerce.SocietyService.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Vector;

import com.commercial.commerce.Models.Article;
import com.commercial.commerce.SocietyService.Models.RequestLinkProforma;
import com.commercial.commerce.SocietyService.Models.RequestLinkProformaDetails;
import com.commercial.commerce.SocietyService.Models.RequestProforma;
import com.commercial.commerce.SocietyService.Models.RequestProformaDetails;
import com.commercial.commerce.SocietyService.Models.RequestSC;
import com.commercial.commerce.SocietyService.Models.RequestSCDetails;
import com.commercial.commerce.SocietyService.Models.Service;
import com.commercial.commerce.SocietyService.Repository.RequestLinkProformaDetailsRepo;
import com.commercial.commerce.SocietyService.Repository.RequestLinkProformaRepo;
import com.commercial.commerce.SocietyService.Repository.RequestProformaDetailsRepo;
import com.commercial.commerce.SocietyService.Repository.RequestProformaRepo;
import com.commercial.commerce.SocietyService.Repository.RequestSCDetailsRepo;
import com.commercial.commerce.SocietyService.Repository.RequestSCRepo;
import com.commercial.commerce.SocietyService.Repository.ServiceRepo;
import com.commercial.commerce.SocietyService.RequestStruct.RequestSCStruct;
import com.commercial.commerce.Supplier.Models.ArticleSupplier;
import com.commercial.commerce.Supplier.Models.ProformaDetails;
import com.commercial.commerce.Supplier.Models.RequestProformaSupplier;
import com.commercial.commerce.Supplier.Models.Supplier;
import com.commercial.commerce.Supplier.Repository.ArticleSupplierRepo;
import com.commercial.commerce.Supplier.Repository.ProformDetailsRepo;
import com.commercial.commerce.Supplier.Repository.RequestProformaSupplierRepo;
import com.commercial.commerce.Supplier.Repository.SupplierRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class RequestSCService {

    private final RequestSCRepo requestSCRepo;
    private final ServiceRepo serviceRepo;
    private final RequestSCDetailsRepo requestSCDetailsRepo;
    private final RequestProformaRepo requestProformaRepo;
    private final RequestProformaSupplierRepo requestProformaSupplierRepo;
    private final SupplierRepo supplierRepo;
    private final RequestProformaDetailsRepo requestProformaDetailsRepo;
    private final ProformDetailsRepo proformDetailsRepo;
    private final RequestLinkProformaRepo requestLinkProformaRepo;
    private final RequestLinkProformaDetailsRepo requestLinkProformaDetailsRepo;
    private final ArticleSupplierRepo articleSupplierRepo;

    public List<RequestSC> getRequestSCNotValidate() {
        List<RequestSC> requests = requestSCRepo.findByValidateOrderByDateDesc(false);
        return requests;
    }

    public List<RequestSC> getRequestSCValidate() {
        List<RequestSC> requests = requestSCRepo.findByValidateOrderByDateDesc(true);
        return requests;
    }

    public List<RequestSCDetails> getDetailsRequest(Long id_request_sc) {
        RequestSC req = requestSCRepo.findById(id_request_sc).get();
        List<RequestSCDetails> requests = requestSCDetailsRepo.findByRequestsc(req);
        return requests;
    }

    public List<RequestProformaSupplier> getRequestProforma() {
        return requestProformaSupplierRepo.findAllByOrderByIdAsc();
    }

    public List<RequestProformaSupplier> getRequestProforma(Long id_supplier) {
        Supplier supplier = supplierRepo.findById(id_supplier).get();
        return requestProformaSupplierRepo.findBySupplier(supplier);
    }

    public List<RequestProformaDetails> getRequestProformaDetails(Long id_proforma) {
        RequestProforma requestProforma = requestProformaRepo.findById(id_proforma).get();
        List<RequestProformaDetails> details = requestProformaDetailsRepo.findByRequestProforma(requestProforma);
        return details;
    }

    // UPDATE
    @Transactional
    public void acceptToSendProforma(Long id_proforma_details_supplier) {
        // idk oe mandefa pdf ve sa juste alefa any stock anleh olona en courrant
        RequestProformaSupplier requestProformaSupplier = requestProformaSupplierRepo
                .findById(id_proforma_details_supplier).get();
        requestProformaSupplier.setAnswered(true);
        // check if it has the article necessary
        checkIfSupplierHasTheArticle(requestProformaSupplier);
        // add pdf
        ProformaDetails proformaDetails = ProformaDetails.builder().requestProformaSupplier(requestProformaSupplier)
                .pdfPath("--").build();
        proformDetailsRepo.save(proformaDetails);
    }

    public void checkIfSupplierHasTheArticle(RequestProformaSupplier requestProformaSupplier) {

        List<RequestProformaDetails> requestProformaDetails = requestProformaDetailsRepo
                .findByRequestProforma(requestProformaSupplier.getRequestProforma());
        List<ArticleSupplier> articleSuppliers = articleSupplierRepo
                .findBySupplier(requestProformaSupplier.getSupplier());

        for (RequestProformaDetails reqDetails : requestProformaDetails) {
            Boolean isIn = false;
            for (ArticleSupplier article_supplier : articleSuppliers) {
                if (reqDetails.getArticle().getId() == article_supplier.getArticle().getId())
                    isIn = true;
            }
            if (!isIn)
                throw new RuntimeException(reqDetails.getArticle().getName() + " is not available in the store");
        }

    }

    // INSERT
    @Transactional
    public void validateRequest(List<Long> list_id_request) {

        List<RequestSC> requests = new Vector<RequestSC>();
        for (Long id_request : list_id_request) {
            RequestSC reqSC = requestSCRepo.findById(id_request).get();
            reqSC.setValidate(true);
            requests.add(requestSCRepo.save(reqSC));
        }
        requestProforma(requests);
    }

    public void requestProforma(List<RequestSC> requestSCs) {
        Timestamp datenow = Timestamp.from(getTimeNow().toInstant().plus(3, ChronoUnit.HOURS));
        RequestProforma requestProforma = RequestProforma.builder().date(datenow).build();

        // create link prforma and details
        RequestLinkProforma requestLinkProforma = RequestLinkProforma.builder().build();
        requestLinkProforma.setValidation(0);
        requestLinkProforma = requestLinkProformaRepo.save(requestLinkProforma);

        // add link to proforma
        requestProforma.setRequestLinkProforma(requestLinkProforma);
        requestProforma = requestProformaRepo.save(requestProforma);

        // create realation request sc and link to proforma
        addRequestLinkProformaDetails(requestLinkProforma, requestSCs);

        ///
        addRequestProformaDetails(requestProforma, requestSCs);
        // add the request proforma to supplier
        sendRequestToSupplier(requestProforma);

    }

    public void addRequestLinkProformaDetails(RequestLinkProforma requestLinkProforma, List<RequestSC> requestSCs) {
        for (RequestSC reqsc : requestSCs) {
            RequestLinkProformaDetails requestLinkProformaDetails = RequestLinkProformaDetails.builder()
                    .requestLinkProforma(requestLinkProforma).requestSC(reqsc).build();
            requestLinkProformaDetailsRepo.save(requestLinkProformaDetails);
        }

    }

    public void addRequestProformaDetails(RequestProforma requestProforma, List<RequestSC> requestSCs) {
        List<Article> articles = getGroupedArticle(requestSCs);
        for (Article article : articles) {
            RequestProformaDetails requestProformaDetails = RequestProformaDetails.builder().article(article)
                    .requestProforma(requestProforma).build();
            requestProformaDetailsRepo.save(requestProformaDetails);
        }
    }

    public List<Article> getGroupedArticle(List<RequestSC> requestSCs) {
        List<Article> articles = new Vector<Article>();
        for (RequestSC requestSC : requestSCs) {
            List<RequestSCDetails> requestDetails = requestSCDetailsRepo.findByRequestsc(requestSC);
            for (RequestSCDetails requestSCD : requestDetails) {
                if (!isIn(articles, requestSCD.getArticle())) {
                    articles.add(requestSCD.getArticle());
                }
            }
        }
        return articles;
    }

    public Boolean isIn(List<Article> articles, Article article) {
        for (Article article_in : articles) {
            if (article_in.getId() == article.getId())
                return true;
        }
        return false;
    }

    public void sendRequestToSupplier(RequestProforma requestProforma) {
        Service commercial = serviceRepo.findFirstByLogo("commercialLogo");
        List<Supplier> supps = supplierRepo.findAll();
        for (Supplier supplier : supps) {
            RequestProformaSupplier rps = RequestProformaSupplier.builder().requestProforma(requestProforma)
                    .service(commercial).supplier(supplier).answered(false).build();
            requestProformaSupplierRepo.save(rps);
        }
    }

    @Transactional
    public void registerRequestSc(RequestSCStruct requestSCStruct) {
        Service s_commercial = serviceRepo.findFirstByLogo("commercialLogo");
        Service s_sender = serviceRepo.findById(requestSCStruct.getService_sender().getId()).get();
        Timestamp date_now = Timestamp.from(getTimeNow().toInstant().plus(3, ChronoUnit.HOURS));
        Boolean validate = false;

        RequestSC requestSC = RequestSC.builder().serviceCommercial(s_commercial).serviceSender(s_sender).date(date_now)
                .validate(validate).build();

        RequestSC newRequestSC = requestSCRepo.save(requestSC);

        for (RequestSCDetails requestDetail : requestSCStruct.getDetails()) {
            checkQuantityRequestService(requestDetail);
            requestDetail.setRequestsc(newRequestSC);
            registerSCDetails(requestDetail);
        }
    }

    public void checkQuantityRequestService(RequestSCDetails requestSCDetails) {
        if (requestSCDetails.getQuantity() <= 0) {
            throw new RuntimeException("Quantity can't be negative or 0");
        }
    }

    public void registerSCDetails(RequestSCDetails requestSCDetails) {
        // IDK
        requestSCDetailsRepo.save(requestSCDetails);
    }

    public Timestamp getTimeNow() {
        // Timestamp.from((time.toInstant().minus(hours, ChronoUnit.HOURS)))
        return Timestamp.from(Instant.now());
    }
}
