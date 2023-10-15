package com.invoicebuilder.InvoiceReader.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoreResponseDTO {

    private Integer responseCode;
    private String message;
    private String reason;
    private Object data;

}
