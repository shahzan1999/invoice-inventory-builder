package com.invoicebuilder.InvoiceReader.serviceImpl;

import com.invoicebuilder.InvoiceReader.dto.request.InvoiceRequestDTO;
import com.invoicebuilder.InvoiceReader.dto.response.CoreResponseDTO;
import com.invoicebuilder.InvoiceReader.service.InvoiceReaderService;
import org.springframework.stereotype.Service;

@Service
public class InvoiceReaderServiceImpl implements InvoiceReaderService {
    @Override
    public CoreResponseDTO uploadInvoice(InvoiceRequestDTO invoiceRequestDTO) {

        return new CoreResponseDTO();

    }
}
