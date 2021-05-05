package com.example.demo.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestApiServiceImpl implements RestApiService {
	
	ObjectMapper objectMapper;

	public RestApiServiceImpl() {
		super();
		objectMapper = new ObjectMapper();
	}

	/* 
	 * This function returns the jsonObject from the url.
	 * */
	
	public JSONObject getRequestJson() throws URISyntaxException,ClientProtocolException, 
					IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		CloseableHttpClient httpclient = HttpClients
//				.createDefault();
				.custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
		JSONObject json = null;
		HttpGet httpGet = new HttpGet();
		URIBuilder builder = new URIBuilder("https://api.npoint.io/3f06b5e64e27ecbcae14");
		httpGet.setURI(builder.build());
		CloseableHttpResponse response = httpclient.execute(httpGet);
		if(response.getStatusLine().getStatusCode() >=200) {
			HttpEntity entity = response.getEntity();
			String apiOutput = EntityUtils.toString(entity);
			
			json = new JSONObject(apiOutput);
			
			EntityUtils.consume(entity);
		}
		response.close();
		
		return json;
	}
}