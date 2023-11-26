package com.commercial.commerce.SocietyService.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercial.commerce.SocietyService.ModelUtils.PurchaseSupplierArticle;
import com.commercial.commerce.SocietyService.Service.PurchaseOrderService;
import com.commercial.commerce.SocietyService.Service.RequestLinkProformaService;
import com.commercial.commerce.Utils.Status;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseOrderService purchaseOrderService;
    private final RequestLinkProformaService requestLinkProformaService;

    @GetMapping("/{id}")
    public Status getPurchaseOrder(@PathVariable(name = "id") Long id_link) {

        try {
            List<List<PurchaseSupplierArticle>> list = purchaseOrderService.getPurchaseOrder(id_link);
            return Status.builder().status("info").data(list).details("Moins disant").build();
        } catch (Exception e) {
            return Status.builder().status("error").details(e.getMessage()).build();
        }

    }

    @GetMapping("/n_validation")
    public Status getValidationRequest() {
        try {
            List<?> list = requestLinkProformaService.getAllValidationRequest();
            return Status.builder().status("info").data(list).data(list).build();
        } catch (Exception e) {
            return Status.builder().status("error").details(e.getMessage()).build();
        }

    }

    @PostMapping("/{id}/validation")
    public Status askValidation(@PathVariable(name = "id") Long id_link) {
        try {
            requestLinkProformaService.askValidationToFinance(id_link);
            return Status.builder().status("info").details("Request validation sent").build();
        } catch (Exception e) {
            return Status.builder().status("error").details(e.getMessage()).build();
        }
    }

    @PostMapping("/{id}/validation/f")
    public Status validateFinance(@PathVariable(name = "id") Long id_link) {
        try {
            requestLinkProformaService.validationFromFinance(id_link);
            return Status.builder().status("good").details("Purchase sent to suppliers").build();
        } catch (Exception e) {
            return Status.builder().status("error").details(e.getMessage()).build();
        }

    }

}
