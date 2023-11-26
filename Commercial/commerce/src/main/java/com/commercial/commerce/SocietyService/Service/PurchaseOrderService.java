package com.commercial.commerce.SocietyService.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import com.commercial.commerce.Models.Article;
import com.commercial.commerce.SocietyService.ModelUtils.DetailsArticleRequested;
import com.commercial.commerce.SocietyService.ModelUtils.PurchaseSupplierArticle;
import com.commercial.commerce.SocietyService.Models.RequestLinkProforma;
import com.commercial.commerce.SocietyService.Models.RequestLinkProformaDetails;
import com.commercial.commerce.SocietyService.Models.RequestProforma;
import com.commercial.commerce.SocietyService.Models.RequestSCDetails;
import com.commercial.commerce.SocietyService.Repository.RequestLinkProformaDetailsRepo;
import com.commercial.commerce.SocietyService.Repository.RequestLinkProformaRepo;
import com.commercial.commerce.SocietyService.Repository.RequestProformaRepo;
import com.commercial.commerce.SocietyService.Repository.RequestSCDetailsRepo;
import com.commercial.commerce.Supplier.Models.ArticleSupplier;
import com.commercial.commerce.Supplier.Models.RequestProformaSupplier;
import com.commercial.commerce.Supplier.Models.Supplier;
import com.commercial.commerce.Supplier.Repository.ArticleSupplierRepo;
import com.commercial.commerce.Supplier.Repository.RequestProformaSupplierRepo;
import com.commercial.commerce.Supplier.Repository.SupplierRepo;

import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    // private final RequestProformaDetailsRepo requestProformaDetailsRepo;
    // private final ProformDetailsRepo proformDetailsRepo;
    private final RequestLinkProformaDetailsRepo requestLinkProformaDetailsRepo;
    private final RequestLinkProformaRepo requestLinkProformaRepo;
    private final RequestProformaRepo requestProformaRepo;
    private final RequestProformaSupplierRepo requestProformaSupplierRepo;
    private final RequestSCDetailsRepo requestSCDetailsRepo;
    private final ArticleSupplierRepo articleSupplierRepo;
    private final SupplierRepo supplierRepo;

    public List<List<PurchaseSupplierArticle>> getPurchaseOrder(Long request_link_proforma) {

        RequestLinkProforma reqLink = requestLinkProformaRepo.findById(request_link_proforma).get();
        // request sc
        List<RequestSCDetails> reqScDetails = getRequestSCDetails(reqLink);
        List<DetailsArticleRequested> articlesNeeded = getArticlesNeeded(reqScDetails);

        // get Answered supplier
        List<Supplier> suppliers = getAnsweredSupplier(reqLink);

        List<List<PurchaseSupplierArticle>> details = new Vector<List<PurchaseSupplierArticle>>();
        for (DetailsArticleRequested articleN : articlesNeeded) {
            details.add(getNeededArticleMoisDisant(articleN, suppliers));
        }
        return details;
    }

    public List<PurchaseSupplierArticle> getNeededArticleMoisDisant(DetailsArticleRequested articleNeeded,
            List<Supplier> suppliers) {
        List<ArticleSupplier> articlesSuppliers = new Vector<ArticleSupplier>();

        // tokony verifiena aloha oe manana anlhe produit ve le supplier zay vao afaka
        // mi accepte mandefa proforma
        for (Supplier supplier : suppliers) {
            ArticleSupplier ar = articleSupplierRepo.findByArticleAndSupplier(articleNeeded.getArticle(), supplier);
            if (ar == null)
                throw new RuntimeException("Supplier " + supplier.getName() + " doesn't have the searched article '"
                        + articleNeeded.getArticle().getName());
            articlesSuppliers.add(ar);
        }

        // sort it for moins disant
        // Sort ASC // by default add .resersed() if you want to DESC
        Comparator<ArticleSupplier> comparatorPriceTTC = Comparator
                .comparingDouble(obj -> obj.getPrice_HT() + getPercent(obj.getTva(), obj.getPrice_HT()));
        Collections.sort(articlesSuppliers, comparatorPriceTTC);

        List<PurchaseSupplierArticle> purchaseSuppliers = getNeededArticledBySupplier(articlesSuppliers, articleNeeded);

        return purchaseSuppliers;
    }

    public List<PurchaseSupplierArticle> getNeededArticledBySupplier(List<ArticleSupplier> articlesSuppliers,
            DetailsArticleRequested articleNeeded) {
        List<PurchaseSupplierArticle> purchaseSuppliers = new Vector<PurchaseSupplierArticle>();
        Double needed = articleNeeded.getQuantity();

        for (ArticleSupplier articleSupplier : articlesSuppliers) {
            Double rest = needed - articleSupplier.getQuantity();
            if (rest == 0) {

                purchaseSuppliers.add(PurchaseSupplierArticle.builder().articleSupplier(articleSupplier)
                        .quantity(needed).build());

                needed = 0.0;
                break;
            } else if (rest < 0) {

                purchaseSuppliers.add(PurchaseSupplierArticle.builder().articleSupplier(articleSupplier)
                        .quantity(needed).build());

                needed = 0.0;
                break;
            } else if (rest > 0) {

                purchaseSuppliers.add(PurchaseSupplierArticle.builder().articleSupplier(articleSupplier)
                        .quantity(articleSupplier.getQuantity()).build());

                needed = rest;
            }
        }

        if (needed > 0.0) {
            throw new RuntimeException(
                    "Not enough quantity for " + articleNeeded.getArticle().getName() + " in all answered supplier "
                            + "(" + articleNeeded.getQuantity() + ")");
        }

        return purchaseSuppliers;
    }

    public double getPercent(Double percent, Double value) {
        return (value * percent / 100);
    }

    public List<DetailsArticleRequested> getArticlesNeeded(List<RequestSCDetails> reqScDetails) {
        List<DetailsArticleRequested> articlesNeeded = new Vector<DetailsArticleRequested>();

        for (RequestSCDetails detail : reqScDetails) {
            if (isInArticle(detail.getArticle(), articlesNeeded)) {
                addArticleQuantity(detail.getArticle(), articlesNeeded, detail.getQuantity());
            } else {
                DetailsArticleRequested d = DetailsArticleRequested.builder().article(detail.getArticle())
                        .quantity(detail.getQuantity()).build();
                articlesNeeded.add(d);
            }
        }

        return articlesNeeded;
    }

    public Boolean isInArticle(Article article, List<DetailsArticleRequested> articleRequesteds) {
        for (DetailsArticleRequested detailsArticleRequested : articleRequesteds) {
            if (detailsArticleRequested.getArticle().getId() == article.getId())
                return true;
        }
        return false;
    }

    public void addArticleQuantity(Article article, List<DetailsArticleRequested> articleRequesteds, Double quantity) {
        for (DetailsArticleRequested detailsArticleRequested : articleRequesteds) {
            if (detailsArticleRequested.getArticle().getId() == article.getId()) {
                Double newQuantity = detailsArticleRequested.getQuantity() + quantity;
                detailsArticleRequested.setQuantity(newQuantity);
            }
        }
    }

    public List<RequestSCDetails> getRequestSCDetails(RequestLinkProforma reqLink) {
        List<RequestLinkProformaDetails> reqLinkProformaDetails = requestLinkProformaDetailsRepo
                .findByRequestLinkProforma(reqLink);
        List<RequestSCDetails> reqSCDetails = new Vector<RequestSCDetails>();

        for (RequestLinkProformaDetails rProDetails : reqLinkProformaDetails) {
            List<RequestSCDetails> reqSCD = requestSCDetailsRepo.findByRequestsc(rProDetails.getRequestSC());
            for (RequestSCDetails reqS : reqSCD) {
                reqSCDetails.add(reqS);
            }
        }

        return reqSCDetails;
    }

    public List<Supplier> getAnsweredSupplier(RequestLinkProforma reqLink) {
        RequestProforma reqPros = requestProformaRepo.findByRequestLinkProforma(reqLink);
        List<RequestProformaSupplier> reqSupplier = requestProformaSupplierRepo.findByRequestProforma(reqPros);
        List<Supplier> resSupplier = new Vector<Supplier>();
        for (RequestProformaSupplier req_supplier : reqSupplier) {
            if (req_supplier.getAnswered())
                resSupplier.add(req_supplier.getSupplier());
        }
        if (resSupplier.size() == 0)
            throw new RuntimeException("You need at least 1 supplier to reply for making a purchase order");
        return resSupplier;
    }

    // FOR PDF
    public List<ArticleSupplier> getListArticleForProforma(Long id_link, Long id_supplier) {

        RequestLinkProforma reqLink = requestLinkProformaRepo.findById(id_link).get();
        // request sc
        List<RequestSCDetails> reqScDetails = getRequestSCDetails(reqLink);
        List<DetailsArticleRequested> articlesNeeded = getArticlesNeeded(reqScDetails);

        List<ArticleSupplier> list = new Vector<ArticleSupplier>();

        Supplier supplier = supplierRepo.findById(id_supplier).get();
        for (DetailsArticleRequested article : articlesNeeded) {
            list.add(articleSupplierRepo.findByArticleAndSupplier(article.getArticle(), supplier));
        }
        return list;
    }
}
