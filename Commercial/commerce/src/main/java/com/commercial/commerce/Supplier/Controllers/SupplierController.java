package com.commercial.commerce.Supplier.Controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercial.commerce.Supplier.Models.ArticleSupplier;
import com.commercial.commerce.Supplier.Service.ArticleSupplierService;
import com.commercial.commerce.Utils.Status;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final ArticleSupplierService articleSupplierService;

    @GetMapping("/{id}/article")
    public Status getEtatStock(@PathVariable(name = "id") Long id_supplier) {
        return Status.builder().data(articleSupplierService.getListArticleSupplier(id_supplier)).status("good").build();
    }

    @PostMapping("/article")
    public Status addSupplierArticle(@RequestBody ArticleSupplier articleSupplier) {
        try {
            articleSupplierService.addArticle(articleSupplier);
            return Status.builder().status("good").details(articleSupplier.getArticle().getName() + " added")
                    .build();
        } catch (Exception e) {
            return Status.builder().status("error").details(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/{id}/article_not_in")
    public Status getListArticleNotIn(@PathVariable(name = "id") Long id_supplier) {
        return Status.builder().data(articleSupplierService.getListArticleSupplierNotIn(id_supplier)).status("good")
                .build();
    }

    @PutMapping("/article")
    public Status updateSupplierArticle(@RequestBody ArticleSupplier articleSupplier) {
        try {
            System.out.println(articleSupplier.toString());
            articleSupplierService.updateArticleStock(articleSupplier);
            return Status.builder().status("good").details(articleSupplier.getArticle().getName() + " updated")
                    .build();
        } catch (Exception e) {
            return Status.builder().status("error").details(e.getMessage())
                    .build();

        }
    }

    @DeleteMapping("/article/{id}")
    public Status addSupplierArticle(@PathVariable(name = "id") Long id_article_supplier) {
        try {
            articleSupplierService.deleteArticle(id_article_supplier);
            return Status.builder().status("good").details("Article removed ")
                    .build();
        } catch (Exception e) {
            return Status.builder().status("error").details(e.getMessage())
                    .build();
        }
    }

}
