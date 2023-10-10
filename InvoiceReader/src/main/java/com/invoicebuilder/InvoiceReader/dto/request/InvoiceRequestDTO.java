package com.invoicebuilder.InvoiceReader.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class InvoiceRequestDTO {

    private MultipartFile file;

    private String usercode;


}
