package com.example.demo.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface RestApiService {
	public JSONObject getRequestJson() throws URISyntaxException, ClientProtocolException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException;
}
