package org.frontline.certInstaller.mschainOperation;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.asynchttpclient.*;
import org.frontline.certInstaller.config.Config;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class CaRequester {

    final static Logger logger = Logger.getLogger(CaRequester.class.toString());

//    public void getNewCertificateSync(byte[] csr) throws IOException {
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost(Config.NEW_CERT_URL);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectNode jsonRequest = objectMapper.createObjectNode();
//        jsonRequest.put("csr", new String(csr));
//
//        StringEntity requestEntity = new StringEntity(
//                String.valueOf(jsonRequest),
//                ContentType.APPLICATION_JSON);
//        httpPost.setEntity(requestEntity);
//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("Content-type", "application/json");
//
//        CloseableHttpResponse response = client.execute(httpPost);
//        logger.info("Requested new Certificate from " + Config.NEW_CERT_URL);
//        logger.info("Request Body: " + jsonRequest);
//
//        HttpEntity responseEntity = response.getEntity();
//        String stringResponse = EntityUtils.toString(responseEntity);
//
//        JsonNode actualObj = objectMapper.readTree(stringResponse);
//        client.close();
//
//    }

    public String getNewCertificateAsync(byte[] csr) throws IOException, ExecutionException, InterruptedException {

        DefaultAsyncHttpClientConfig.Builder clientBuilder = Dsl.config().setConnectTimeout(1000);
        AsyncHttpClient client = Dsl.asyncHttpClient(clientBuilder);

        BoundRequestBuilder postRequest = client.preparePost(Config.NEW_CERT_URL);
        postRequest.setHeader("Accept", "application/json");
        postRequest.setHeader("Content-type", "application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonRequest = objectMapper.createObjectNode();
        jsonRequest.put("csr", new String(csr));

        postRequest.setBody( String.valueOf(jsonRequest));

        Future<Response> responseFuture = postRequest.execute();
        logger.info("Requested new Certificate from " + Config.NEW_CERT_URL);
        logger.info("Request Body: " + jsonRequest);

        Response response = responseFuture.get();
        String responseString = response.getResponseBody();
        logger.info("Response Body: "+responseString);
        return responseString;
    }
}
