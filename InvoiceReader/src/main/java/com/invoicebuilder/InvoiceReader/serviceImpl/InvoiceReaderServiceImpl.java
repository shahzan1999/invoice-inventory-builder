package com.invoicebuilder.InvoiceReader.serviceImpl;

import com.invoicebuilder.InvoiceReader.azure.InvoiceAIUtility;
import com.invoicebuilder.InvoiceReader.constant.ResponseContants;
import com.invoicebuilder.InvoiceReader.dto.request.InvoiceRequestDTO;
import com.invoicebuilder.InvoiceReader.dto.response.CoreResponseDTO;
import com.invoicebuilder.InvoiceReader.dto.response.UploadInvoiceResponseDTO;
import com.invoicebuilder.InvoiceReader.service.InvoiceReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class InvoiceReaderServiceImpl implements InvoiceReaderService {

    @Autowired
    private InvoiceAIUtility invoiceAIUtility;

    @Override
    public CoreResponseDTO uploadInvoice(InvoiceRequestDTO invoiceRequestDTO) {

        CoreResponseDTO coreResponseDTO = CoreResponseDTO.builder()
                .responseCode(ResponseContants.INTERNAL_EXCEPTION.getResponseCode())
                .message(ResponseContants.INTERNAL_EXCEPTION.getMessage())
                .build();

        try {

            UploadInvoiceResponseDTO invoiceResponseDTO = invoiceAIUtility.uploadInvoiceImage(invoiceRequestDTO.getFile());
            coreResponseDTO = CoreResponseDTO.builder()
                    .responseCode(ResponseContants.SUCCESS_RESPONSE.getResponseCode())
                    .message(ResponseContants.SUCCESS_RESPONSE.getMessage())
                    .data(invoiceResponseDTO)
                    .build();

        } catch (IOException e) {

            log.info(e.getMessage());

        }

        return coreResponseDTO;

    }
}
