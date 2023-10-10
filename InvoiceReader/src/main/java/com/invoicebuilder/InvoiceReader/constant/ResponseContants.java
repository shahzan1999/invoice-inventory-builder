package com.invoicebuilder.InvoiceReader.constant;

import lombok.Getter;

@Getter
public enum ResponseContants {

    SUCCESS_RESPONSE(20000,"Success"),
    BAD_REQUEST(4000,"Invalid Request!"),
    INTERNAL_EXCEPTION(5000,"Unknown Server Error!");

    private int responseCode;

    private String message;

    ResponseContants(int responseCode,String message){
        this.responseCode = responseCode;
        this.message = message;
    }



}
