package com.invoicebuilder.InvoiceReader.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnalyzeResult {

    List<Document> documents;
}
