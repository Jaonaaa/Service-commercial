package com.commercial.commerce.SocietyService.ModelUtils;

import com.commercial.commerce.Supplier.Models.ArticleSupplier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PurchaseSupplierArticle {

    ArticleSupplier articleSupplier;
    Double quantity;
}
