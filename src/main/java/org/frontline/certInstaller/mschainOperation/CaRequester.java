package org.frontline.certInstaller.mschainOperation;



import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.frontline.certInstaller.config.Config;

import java.io.UnsupportedEncodingException;
import java.net.URI;

public class CaRequester {

    public void getNewCertificate() throws UnsupportedEncodingException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(Config.NEW_CERT_URL);

        String json = "";

//        StringEntity entity = new StringEntity(json);
//        httpPost.setEntity(new CsrRequest(""));
//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("Content-type", "application/json");
//
//        CloseableHttpResponse response = client.execute(httpPost);
//        client.close();

    }
}
