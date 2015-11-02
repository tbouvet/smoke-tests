package org.seedstack.tests.jms;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.Test;

public class SendReceiveIT {

	@Test
	public void test() throws Exception {
		// Integer port = Integer.valueOf(System.getProperty("docker.port"));
		// String hostname = System.getProperty("docker.host");
		Integer port = 8080;
		String hostname = "localhost";
		String uri = "http://" + hostname + ":" + port + "/smoke-tests/rest/informations/send";
		HttpPost request = new HttpPost(uri);
		request.addHeader("Content-Type", "application/json");
		JSONObject keyArg = new JSONObject();
		keyArg.put("id", 1);
		keyArg.put("detail", "detail");
		StringEntity entity = new StringEntity(keyArg.toString());

		request.setEntity(entity);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		Assertions.assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
		// uri = "http://" + hostname + ":" + port +
		// "/smoke-tests/rest/client/load/1";
		// HttpGet get = new HttpGet(uri);
		// response = HttpClientBuilder.create().build().execute(get);
	}

}
