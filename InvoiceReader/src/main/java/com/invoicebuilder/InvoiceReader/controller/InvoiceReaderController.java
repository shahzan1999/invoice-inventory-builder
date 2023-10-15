package com.invoicebuilder.InvoiceReader.controller;

import com.invoicebuilder.InvoiceReader.constant.Constants;
import com.invoicebuilder.InvoiceReader.dto.request.InvoiceRequestDTO;
import com.invoicebuilder.InvoiceReader.dto.response.CoreResponseDTO;
import com.invoicebuilder.InvoiceReader.service.InvoiceReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_VERSION+"/"+Constants.INVOICE)
@Slf4j
public class InvoiceReaderController {

    @Autowired
    private InvoiceReaderService invoiceReaderService;

    @GetMapping("/test")
    public String tst(){
        return "ok";
    }


    @PostMapping("/upload")
    public CoreResponseDTO uploadInvoice(@ModelAttribute InvoiceRequestDTO invoiceRequestDTO) {

        log.info("Invoice Upload API called for USERCODE:"+invoiceRequestDTO.getUsercode());

        return invoiceReaderService.uploadInvoice(invoiceRequestDTO);

    }


}
