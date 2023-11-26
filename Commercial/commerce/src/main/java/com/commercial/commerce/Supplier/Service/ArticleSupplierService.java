package com.commercial.commerce.Supplier.Service;

import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.commercial.commerce.Models.Article;
import com.commercial.commerce.Repository.ArticleRepo;
import com.commercial.commerce.Supplier.Models.ArticleSupplier;
import com.commercial.commerce.Supplier.Models.Supplier;
import com.commercial.commerce.Supplier.Repository.ArticleSupplierRepo;
import com.commercial.commerce.Supplier.Repository.SupplierRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleSupplierService {

    private final ArticleSupplierRepo articleSupplierRepo;
    private final SupplierRepo supplierRepo;
    private final ArticleRepo articleRepo;

    public List<ArticleSupplier> getListArticleSupplier(Long id_supplier) {
        Supplier supplier = supplierRepo.findById(id_supplier).get();
        return articleSupplierRepo.findBySupplier(supplier);
    }

    public void addArticle(ArticleSupplier articleSupplier) {
        articleSupplierRepo.save(articleSupplier);
    }

    public void updateArticleStock(ArticleSupplier articleSupplier) {
        ArticleSupplier articleIn = articleSupplierRepo.findById(articleSupplier.getId()).get();
        articleIn.setPrice_HT(articleSupplier.getPrice_HT());
        articleIn.setTva(articleSupplier.getTva());
        articleIn.setQuantity(articleSupplier.getQuantity());
        articleSupplierRepo.save(articleIn);
    }

    public void deleteArticle(Long id_article_supplier) {
        articleSupplierRepo.deleteById(id_article_supplier);
    }

    public List<Article> getListArticleSupplierNotIn(Long id_supplier) {
        Supplier supplier = supplierRepo.findById(id_supplier).get();

        List<ArticleSupplier> articleSuppliers = articleSupplierRepo.findBySupplier(supplier);
        List<Article> articles = articleRepo.findAll();

        return getArticleNotIn(articleSuppliers, articles);
    }

    public List<Article> getArticleNotIn(List<ArticleSupplier> articleSuppliers, List<Article> articles) {
        List<Article> notIn = new Vector<Article>();
        for (Article article : articles) {
            Boolean in = false;

            for (ArticleSupplier articleSupplier : articleSuppliers) {
                if (article.getId() == articleSupplier.getArticle().getId()) {
                    in = true;
                    break;
                }
            }
            if (!in) {
                notIn.add(article);
            }
        }
        return notIn;
    }

}
