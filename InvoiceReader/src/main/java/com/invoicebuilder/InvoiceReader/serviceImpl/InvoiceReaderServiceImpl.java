package com.invoicebuilder.InvoiceReader.serviceImpl;

import com.invoicebuilder.InvoiceReader.azure.InvoiceAIUtility;
import com.invoicebuilder.InvoiceReader.dto.request.InvoiceRequestDTO;
import com.invoicebuilder.InvoiceReader.dto.response.CoreResponseDTO;
import com.invoicebuilder.InvoiceReader.service.InvoiceReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class InvoiceReaderServiceImpl implements InvoiceReaderService {

    @Autowired
    private InvoiceAIUtility invoiceAIUtility;

    @Override
    public CoreResponseDTO uploadInvoice(InvoiceRequestDTO invoiceRequestDTO) {

        try {
            invoiceAIUtility.uploadInvoiceImage(invoiceRequestDTO.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new CoreResponseDTO();

    }
}
