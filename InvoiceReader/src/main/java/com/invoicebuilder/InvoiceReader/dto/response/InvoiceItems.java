package com.invoicebuilder.InvoiceReader.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItems {

    private String itemName;

    private Double unitPrice;

    private Double quantity;

    private Double totalAmount;

}
