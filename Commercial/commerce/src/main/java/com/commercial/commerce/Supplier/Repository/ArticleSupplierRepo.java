package com.commercial.commerce.Supplier.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.commerce.Models.Article;
import com.commercial.commerce.Supplier.Models.ArticleSupplier;
import com.commercial.commerce.Supplier.Models.Supplier;

public interface ArticleSupplierRepo extends JpaRepository<ArticleSupplier, Long> {

    ArticleSupplier findByArticleAndSupplier(Article article, Supplier supplier);

    List<ArticleSupplier> findBySupplier(Supplier supplier);

}
