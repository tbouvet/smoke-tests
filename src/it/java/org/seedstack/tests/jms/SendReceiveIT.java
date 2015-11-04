package org.seedstack.tests.jms;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test a JMS communication (post a rest to send a JMS message and insert the
 * content in a db.) The goal is to check that object is created so
 * communication is ok.
 * 
 * @author thierry.bouvet@mpsa.com
 *
 */
public class SendReceiveIT {

    private static final int PAYLOAD_ID = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(SendReceiveIT.class);

    /**
     * Test all JMS communication.
     * 
     * @throws Exception
     *             if an error occurred
     */
    @Test
    public void test() throws Exception {
        Integer port = Integer.valueOf(System.getProperty("docker.port"));
        String hostname = System.getProperty("docker.host");
        String uri = "http://" + hostname + ":" + port + "/smoke-tests/rest";
        sendMessage(uri);

        checkPayload(uri);

    }

    /**
     * Check if the object sent by JMS is created.
     * 
     * @param uri
     *            Common uri
     * @throws Exception
     *             if an error occurred
     */
    private void checkPayload(final String uri) throws Exception {

        Callable<Boolean> callable = new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                String loadURI = uri + "/payload/load/" + PAYLOAD_ID;
                HttpUriRequest request = new HttpGet(loadURI);
                while (true) {
                    Thread.sleep(300);
                    HttpResponse response = HttpClientBuilder.create().build().execute(request);
                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        LOGGER.info("Payload created with success!");
                        return true;
                    }
                }
            }
        };
        FutureTask<Boolean> futureTask = new FutureTask<Boolean>(callable);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(futureTask);

        if (futureTask.isDone()) {
            executor.shutdown();
        }
        try {
            futureTask.get(5, TimeUnit.SECONDS);
            // Test is ok because no interruption
        } catch (InterruptedException e) {
            Assertions.fail("Callable error", e);
        } catch (ExecutionException e) {
            Assertions.fail("Callable error", e);
        } catch (TimeoutException e) {
            Assertions.fail("Interrupted call, payload is not created in the DB!");
        }

    }

    /**
     * Call an URI to send a JMS message
     * 
     * @param uri
     *            Common uri
     * @throws Exception
     *             if an error occurred
     */
    private void sendMessage(String uri) throws Exception {
        HttpPost postRequest = new HttpPost(uri + "/informations/send");
        postRequest.addHeader("Content-Type", "application/json");
        JSONObject keyArg = new JSONObject();
        keyArg.put("id", PAYLOAD_ID);
        keyArg.put("detail", "detail");
        StringEntity entity = new StringEntity(keyArg.toString());

        postRequest.setEntity(entity);
        HttpResponse response = HttpClientBuilder.create().build().execute(postRequest);

        Assertions.assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
    }

}
