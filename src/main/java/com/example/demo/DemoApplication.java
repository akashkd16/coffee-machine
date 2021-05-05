package com.example.demo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.demo.service.CoffeeMachineService;
import com.example.demo.service.RestApiService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.example.demo");
		context.refresh();
		RestApiService restApiService = context.getBean(RestApiService.class);
		CoffeeMachineService coffeeMachineService = context.getBean(CoffeeMachineService.class);
		JSONObject jsonObject = null;
		
		try {
			/*
			 * userInput[] is storing random beverages
			 * among {"hot_tea","hot_coffee", "black_tea", "green_tea"}
			 * 
			 */
			
			Random rand = new Random();
			String[] drinks = new String[] {"hot_tea","hot_coffee", "black_tea", "green_tea"};
			
			String[] userInput = new String[100];
			for(int i=0;i<100;i++) {
				int rand_int = rand.nextInt(100);
				userInput[i] = drinks[rand_int%4];
			}
			
			jsonObject = restApiService.getRequestJson();
			JSONObject machine = jsonObject.getJSONObject("machine");
			JSONObject beverage = machine.getJSONObject("beverages");
			Map<String,Object> mapOfBeverages = beverage.toMap();
			
			Integer numberOfOutlets = (Integer) machine.getJSONObject("outlets").toMap().get("count_n");
			
			Map<String, Object> mapOfTotalQuantity = machine.getJSONObject("total_items_quantity").toMap();
			
			coffeeMachineService.startMachine(mapOfBeverages, mapOfTotalQuantity, numberOfOutlets, userInput);
			
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | URISyntaxException
				| IOException e) {
			e.printStackTrace();
		}
		
		context.close();
	}

}
