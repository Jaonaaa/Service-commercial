package com.commercial.commerce.SocietyService.Controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercial.commerce.SocietyService.Service.ExportPDFService;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pdf")
@RequiredArgsConstructor
public class PDFController {

    private final ExportPDFService exportPDFService;

    @GetMapping("/proforma/{id_link}/{id_supplier}")
    public void exportToPDF(HttpServletResponse response, @PathVariable("id_link") Long id_link,
            @PathVariable("id_supplier") Long id_supplier) throws DocumentException, IOException {

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=proforma_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        // List<User> listUsers = service.listAll();
        exportPDFService.export(response, id_link, id_supplier);

    }

}
