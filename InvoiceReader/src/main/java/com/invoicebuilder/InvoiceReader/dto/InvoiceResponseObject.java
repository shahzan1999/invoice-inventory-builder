package com.invoicebuilder.InvoiceReader.dto;

import lombok.Data;

@Data
public class InvoiceResponseObject {

    private String status;

    private AnalyzeResult analyzeResult;

}
