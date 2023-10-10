package com.invoicebuilder.InvoiceReader.dto.response;

import lombok.Data;

@Data
public class CoreResponseDTO {

    private String responseCode;
    private String message;
    private String reason;
    private Object data;

}
