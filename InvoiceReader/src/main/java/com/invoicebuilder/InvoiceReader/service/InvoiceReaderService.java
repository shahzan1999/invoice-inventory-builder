package com.invoicebuilder.InvoiceReader.service;


import com.invoicebuilder.InvoiceReader.dto.request.InvoiceRequestDTO;
import com.invoicebuilder.InvoiceReader.dto.response.CoreResponseDTO;

public interface InvoiceReaderService {


    CoreResponseDTO uploadInvoice(InvoiceRequestDTO invoiceRequestDTO);

}
