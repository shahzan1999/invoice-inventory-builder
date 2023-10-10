package com.invoicebuilder.InvoiceReader.controller;

import com.invoicebuilder.InvoiceReader.constant.Constants;
import com.invoicebuilder.InvoiceReader.dto.request.InvoiceRequestDTO;
import com.invoicebuilder.InvoiceReader.dto.response.CoreResponseDTO;
import com.invoicebuilder.InvoiceReader.service.InvoiceReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_VERSION+"/"+Constants.INVOICE)
public class InvoiceReaderController {

    @Autowired
    private InvoiceReaderService invoiceReaderService;

    @GetMapping("/test")
    public String tst(){
        return "ok";
    }

    @GetMapping("/test2")
    public ResponseEntity<?> tst2(){
        return new ResponseEntity<>("ok", HttpStatus.OK);

    }


    @PostMapping("/upload")
    public CoreResponseDTO uploadInvoice(@ModelAttribute InvoiceRequestDTO invoiceRequestDTO) {

            return invoiceReaderService.uploadInvoice(invoiceRequestDTO);

    }


}
