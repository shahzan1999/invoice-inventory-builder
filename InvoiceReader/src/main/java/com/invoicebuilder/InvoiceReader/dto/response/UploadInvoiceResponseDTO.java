package com.invoicebuilder.InvoiceReader.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UploadInvoiceResponseDTO {

    private Integer totalItems;

    private Integer errorItems;

    private List<InvoiceItems> items;


}
