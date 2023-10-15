package com.invoicebuilder.InvoiceReader.azure;

import com.invoicebuilder.InvoiceReader.dto.response.InvoiceItems;
import com.invoicebuilder.InvoiceReader.dto.response.UploadInvoiceResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class InvoiceAIUtility {

    @Value("${azure.upload.invoice.uri}")
    private String azureUploadURI;

    @Value("${azure.subscription.token}")
    private String azureSubscriptionToken ;


    @Autowired
    RestTemplate restTemplate;

    public UploadInvoiceResponseDTO uploadInvoiceImage(MultipartFile invoice) throws IOException {


        String url = azureUploadURI;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type","image/jpeg");
        httpHeaders.set("Ocp-Apim-Subscription-Key",azureSubscriptionToken);


        ByteArrayResource fileAsResource = new ByteArrayResource(invoice.getBytes()){
            @Override
            public String getFilename(){
                return invoice.getOriginalFilename();
            }
        };

        HttpEntity<Object> httpEntity = new HttpEntity<>(fileAsResource,httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,httpEntity,String.class);

        log.info("Invoice Upload Api Response Received: "+ responseEntity.getStatusCode());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        String fetchInvoiceItemURI = responseEntity.getHeaders().get("Operation-Location").get(0);
//                "https://invoice-inventory-builder.cognitiveservices.azure.com/formrecognizer/documentModels/prebuilt-invoice/analyzeResults/139473e5-fdd3-4dcb-91db-d5b55bba2624?api-version=2023-07-31";


        HttpHeaders getResultHttpHeaders = new HttpHeaders();
        getResultHttpHeaders.set("Ocp-Apim-Subscription-Key",azureSubscriptionToken);

        HttpEntity<Object> getResulthttpEntity = new HttpEntity<>(getResultHttpHeaders);


        ResponseEntity<String> invoiceResponseObjectResponseEntity
                = restTemplate.exchange(fetchInvoiceItemURI, HttpMethod.GET,getResulthttpEntity,String.class);

        log.info("Read Invoice API Response Received: "+ invoiceResponseObjectResponseEntity.getStatusCode());


        JSONObject response = new JSONObject(invoiceResponseObjectResponseEntity.getBody());
        JSONArray itemValueArrays =  response.getJSONObject("analyzeResult")
                    .getJSONArray("documents").getJSONObject(0)
                        .getJSONObject("fields")
                            .getJSONObject("Items")
                .getJSONArray("valueArray");

        int errorEntry = 0;
        List<InvoiceItems> invoiceItemsList = new ArrayList<>();
        for (int i = 0; i < itemValueArrays.length(); i++) {

            InvoiceItems invoiceItem = new InvoiceItems();

            try {

                JSONObject valueObject = itemValueArrays.getJSONObject(i)
                        .getJSONObject("valueObject");

                JSONObject amountJson = valueObject.getJSONObject("Amount").optJSONObject("valueCurrency");
                invoiceItem.setTotalAmount(amountJson.getDouble("amount"));

                JSONObject itemDescJson = valueObject.getJSONObject("Description");
                invoiceItem.setItemName(itemDescJson.getString("content").replace("\n", " ").replace("\r", " "));

                JSONObject qtyJson = valueObject.getJSONObject("Quantity");
                invoiceItem.setQuantity(qtyJson.getDouble("valueNumber"));

                JSONObject unitPriceJson = valueObject.getJSONObject("UnitPrice").optJSONObject("valueCurrency");
                invoiceItem.setUnitPrice(unitPriceJson.getDouble("amount"));

                invoiceItemsList.add(invoiceItem);
            } catch (Exception e){
                errorEntry++;
                log.info( e.getMessage() );
            }

        }

        UploadInvoiceResponseDTO uploadInvoiceResponseDTO = new UploadInvoiceResponseDTO();
        uploadInvoiceResponseDTO.setTotalItems(itemValueArrays.length());
        uploadInvoiceResponseDTO.setErrorItems(errorEntry);
        uploadInvoiceResponseDTO.setItems(invoiceItemsList);



        return uploadInvoiceResponseDTO;

    }



}
