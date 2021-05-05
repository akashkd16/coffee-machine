package com.example.demo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.demo.service.CoffeeMachineService;
import com.example.demo.service.RestApiService;

@SpringBootApplication
public class DemoApplication {
//	@Autowired
//	RestApiService restApiService;

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.example.demo");
		context.refresh();
		RestApiService restApiService = context.getBean(RestApiService.class);
		CoffeeMachineService coffeeMachineService = context.getBean(CoffeeMachineService.class);
//		Machine machine = null;
		Map<String, JSONObject> listOfBeverageDetails = new HashMap<>();
		JSONObject jsonObject = null;
		try {
//			jsonObject = restApiService.getRequestJson();
//			JSONObject machine = jsonObject.getJSONObject("machine");   // to get complete json
//			JSONObject beverages = machine.getJSONObject("beverages");  // to fetch beverages
//			JSONObject totalItemsQuatities = machine.getJSONObject("total_items_quantity");
			String[] userInput = new String[95];
			for(int i=0;i<90;i+=2) {
				userInput[i]="hot_tea";
			}
			for(int i=1;i<90;i+=2) {
				userInput[i]="hot_coffee";
			}
			
			jsonObject = restApiService.getRequestJson();
			JSONObject machine = jsonObject.getJSONObject("machine");
			JSONObject beverage = machine.getJSONObject("beverages");
			Map<String,Object> mapOfBeverages = beverage.toMap();
			
			Integer numberOfOutlets = (Integer) machine.getJSONObject("outlets").toMap().get("count_n");
			
			Map<String, Object> mapOfTotalQuantity = machine.getJSONObject("total_items_quantity").toMap();
			
			coffeeMachineService.startMachine(mapOfBeverages, mapOfTotalQuantity, numberOfOutlets, userInput);
		
			//System.out.println("Total number of Outlet: " + machine.getOutlets().getCountN());
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | URISyntaxException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//coffeeMachineService.startMachine(machine);
		//SpringApplication.run(DemoApplication.class, args);
		context.close();
	}

}
