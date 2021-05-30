package com.elevate.api.aggregator.client;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

import com.elevate.api.aggregator.exception.HttpClientException;
import com.elevate.api.aggregator.exception.Not2xxCodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class BaseClient {

    private final static ObjectMapper mapper = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static <REQ, RESP> RESP post(REQ jsonObject, CloseableHttpClient client, String url, Class<RESP> responseClass) {
        try (client) {
            StringEntity entity = new StringEntity(mapper.writeValueAsString(jsonObject), ContentType.APPLICATION_JSON);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(entity);
            client.execute(httpPost);
            try (CloseableHttpResponse httpResponse = client.execute(httpPost)) {
                if (!HttpStatus.valueOf(httpResponse.getStatusLine().getStatusCode()).is2xxSuccessful()) {
                    throw new Not2xxCodeException("Request to " + url + " returned status " + httpResponse.getStatusLine());
                }
                if (responseClass != null) {
                    return mapper.readValue(httpResponse.getEntity().getContent(), responseClass);
                }
            }
        } catch (IOException exception) {
            throw new HttpClientException(exception);
        }
        return null;
    }

    public static <REQ, RESP> RESP post(REQ jsonObject, String url, Class<RESP> responseClass) {
        return post(jsonObject, HttpClients.createDefault(), url, responseClass);
    }

}
