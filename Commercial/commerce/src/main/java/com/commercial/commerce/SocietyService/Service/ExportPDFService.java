package com.commercial.commerce.SocietyService.Service;

import com.commercial.commerce.Supplier.Models.ArticleSupplier;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExportPDFService {

    private final PurchaseOrderService purchaseOrderService;

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(10);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Article ", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Quantité ", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Prix HT", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("TVA", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Prix TTC", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, List<ArticleSupplier> articles) {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(7);

        for (ArticleSupplier art : articles) {

            Double tva_value = (art.getPrice_HT() * art.getTva()) / 100;
            cell.setPhrase(new Phrase(art.getArticle().getName()));
            table.addCell(cell);

            cell.setPhrase(new Phrase(String.valueOf(art.getQuantity())));
            table.addCell(cell);

            cell.setPhrase(new Phrase(art.getPrice_HT() + " Ar"));
            table.addCell(cell);

            cell.setPhrase(new Phrase(art.getTva().toString() + "%"));
            table.addCell(cell);

            cell.setPhrase(new Phrase(String.valueOf(art.getPrice_HT() + tva_value) + " Ar"));
            table.addCell(cell);
        }
    }

    public void export(HttpServletResponse response, Long id_link, Long id_supplier)
            throws DocumentException, IOException {

        List<ArticleSupplier> articles = purchaseOrderService.getListArticleForProforma(id_link, id_supplier);

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Proforma / " + articles.get(0).getSupplier().getName(), font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] { 2.5f, 2.5f, 2.5f, 1.5f, 2.2f });
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table, articles);

        document.add(table);

        document.close();

    }
}
