/*
 * Creation : 29 juin 2015
 */
package org.seedstack.tests.domains;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.seedstack.tests.rest.ProductRepresentation;
import org.seedstack.tests.rest.ProductResource;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Check all {@link ProductResource} functionalities.
 * 
 * @author thierry.bouvet@mpsa.com
 *
 */
public class ProductResourceIT {

    /**
     * Check {@link ProductResource} functionalities.
     * 
     * @throws Exception
     *             if an error occurred
     */
    @Test
    public void testProductResource() throws Exception {
        Integer port = Integer.valueOf(System.getProperty("docker.port"));
        String hostname = System.getProperty("docker.host");
        String uri = "http://" + hostname + ":" + port + "/smoke-tests/product";

        createProducts(uri);

        loadProduct(uri);

    }

    private void loadProduct(String uri) throws Exception {
        final Long id = 1L;
        String loadURI = uri + "/load/" + id;
        HttpUriRequest request = new HttpGet(loadURI);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Assertions.assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Assertions.assertThat(ContentType.getOrDefault(response.getEntity()).getMimeType())
                .isEqualTo(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        ProductRepresentation productRepresentation = mapper.readValue(EntityUtils.toString(response.getEntity()),
                ProductRepresentation.class);
        Assertions.assertThat(productRepresentation.getId()).isEqualTo(id);
    }

    private void createProducts(String uri) throws Exception {
        String initURI = uri + "/init";
        HttpUriRequest request = new HttpGet(initURI);

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assertions.assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);

        Assertions.assertThat(ContentType.getOrDefault(response.getEntity()).getMimeType())
                .isEqualTo(MediaType.TEXT_PLAIN);

        final String textFromResponse = "Products created";
        Assertions.assertThat(EntityUtils.toString(response.getEntity())).isEqualTo(textFromResponse);
    }

}
