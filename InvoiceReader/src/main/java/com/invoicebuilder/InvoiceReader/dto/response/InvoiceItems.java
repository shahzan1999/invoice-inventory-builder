package com.invoicebuilder.InvoiceReader.dto.response;

import lombok.Data;

@Data
public class InvoiceItems {

    private String itemName;

    private Double purchaseAmount;

    private Double quantity;

    private String totalAmount;

}
