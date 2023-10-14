package com.invoicebuilder.InvoiceReader.azure;

import com.invoicebuilder.InvoiceReader.dto.InvoiceResponseObject;
import com.invoicebuilder.InvoiceReader.dto.response.UploadInvoiceResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class InvoiceAIUtility {

    @Value("${azure.upload.invoice.uri}")
    private String azureUploadURI;

    @Value("${azure.subscription.token}")
    private String azureSubscriptionToken ;


    @Autowired
    RestTemplate restTemplate;

    public String uploadInvoiceImage(MultipartFile invoice) throws IOException {

//        RestTemplate restTemplate = new RestTemplate();
//        String url = azureUploadURI;
//        log.info("Calling azure on uri "+url);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Content-Type","image/jpeg");
//        httpHeaders.set("Ocp-Apim-Subscription-Key",azureSubscriptionToken);
//
//
//        ByteArrayResource fileAsResource = new ByteArrayResource(invoice.getBytes()){
//            @Override
//            public String getFilename(){
//                return invoice.getOriginalFilename();
//            }
//        };
//
//        HttpEntity<Object> httpEntity = new HttpEntity<>(fileAsResource,httpHeaders);
//
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,httpEntity,String.class);
//        log.info(responseEntity.toString());
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        log.info(responseEntity.getHeaders().get("Operation-Location").get(0));

        String fetchInvoiceItemURI = "https://invoice-inventory-builder.cognitiveservices.azure.com/formrecognizer/documentModels/prebuilt-invoice/analyzeResults/139473e5-fdd3-4dcb-91db-d5b55bba2624?api-version=2023-07-31";
//                responseEntity.getHeaders().get("Operation-Location").get(0);

        HttpHeaders getResultHttpHeaders = new HttpHeaders();
        getResultHttpHeaders.set("Ocp-Apim-Subscription-Key",azureSubscriptionToken);

        HttpEntity<Object> getResulthttpEntity = new HttpEntity<>(getResultHttpHeaders);

        try {

            ResponseEntity<String> invoiceResponseObjectResponseEntity1
                    = restTemplate.exchange(fetchInvoiceItemURI, HttpMethod.GET,getResulthttpEntity,String.class);

            JSONObject response = new JSONObject(invoiceResponseObjectResponseEntity1.getBody());
            JSONArray itemValueArrays =  response.getJSONObject("analyzeResult")
                        .getJSONArray("documents").getJSONObject(0)
                            .getJSONObject("fields")
                                .getJSONObject("Items")
                    .getJSONArray("valueArray");

            for (int i = 0; i < itemValueArrays.length(); i++) {
                JSONObject valueObject = itemValueArrays.getJSONObject(i)
                        .getJSONObject("valueObject");

                JSONObject amountJson = valueObject.getJSONObject("Amount").optJSONObject("valueCurrency");
                log.info("Amount: "+amountJson.getDouble("amount"));

                JSONObject itemDescJson = valueObject.getJSONObject("Description");
                log.info("Desc: "+itemDescJson.getString("content").replace("\n", " ").replace("\r", " "));

                JSONObject qtyJson = valueObject.getJSONObject("Quantity");
                log.info("Qty: "+qtyJson.getDouble("valueNumber"));

                JSONObject unitPriceJson = valueObject.getJSONObject("UnitPrice").optJSONObject("valueCurrency");
                log.info("Unit Price: "+unitPriceJson.getDouble("amount"));


            }

            log.info(invoiceResponseObjectResponseEntity1.toString());

        } catch (Exception e){
            e.printStackTrace();
        }

//        ResponseEntity<InvoiceResponseObject> invoiceResponseObjectResponseEntity
//                = restTemplate.exchange(fetchInvoiceItemURI, HttpMethod.GET,getResulthttpEntity,InvoiceResponseObject.class);
//
//        log.info("Resp "+invoiceResponseObjectResponseEntity.toString());

        return null;

    }


    private static UploadInvoiceResponseDTO parseAPIResponse(String response){


        GsonJsonParser gsonJsonParser = new GsonJsonParser();
        Map<String, Object> responseMap =  gsonJsonParser.parseMap(response);
        log.info(responseMap.toString());
        return null;
    }

}
