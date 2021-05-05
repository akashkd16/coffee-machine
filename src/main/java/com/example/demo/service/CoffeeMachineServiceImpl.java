package com.example.demo.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CoffeeMachineServiceImpl implements CoffeeMachineService {
	
	public class VendingMachine{    
	Map<String, Object> mapOfBeverage;
	Map<String, Object> mapOfTotalQuantity;
	public VendingMachine(Map<String, Object> mapOfBeverage,Map<String, Object> mapOfTotalQuantity) {
		this.mapOfBeverage = mapOfBeverage;
		this.mapOfTotalQuantity=mapOfTotalQuantity;
	}
	
	/*
	 * This function is synchronized
	 * checks if given beverage can be made. if yes, then it makes that drink
	 * Also, reduces the used ingredients from the total ingredients
	 * Also, It handles insufficient and unavailability of any ingredient
	 * 
	 */
	
	public synchronized void makeDemandedDrink(String input) {
		String result = null;
		boolean flag = false;
		if(this.mapOfBeverage.containsKey(input)) {
			Map<String, Object> recipe = (Map<String, Object>) this.mapOfBeverage.get(input);
			for(Map.Entry<String, Object> entry : recipe.entrySet()) {
				if(this.mapOfTotalQuantity.containsKey(entry.getKey())) {
					int availableQuantity = (int) this.mapOfTotalQuantity.get(entry.getKey());
					int requiredQuantity = (int) entry.getValue();
					
					if(availableQuantity < requiredQuantity) {
						result = input +  " cannot be prepared because " +  entry.getKey() + " is not sufficient";
						System.out.println(result);
						flag= true;
						break;
					}
				} else {
					result = input + " cannot be prepared because " + entry.getKey() + " is not available";
					System.out.println(result);
					flag = true;
					break;
				}
			}
			if(!flag) {
				for(Map.Entry<String, Object> entry : recipe.entrySet()) {
					int oldQuantity = (int) this.mapOfTotalQuantity.get(entry.getKey());
					int requiredQuantity = (int) entry.getValue();
					
					this.mapOfTotalQuantity.replace(entry.getKey(), (oldQuantity - requiredQuantity)>=0?oldQuantity - requiredQuantity:0);
					
				}
				result = input +  " is prepared";
				System.out.println(result);
			}
		}
	}

    
}
	
	public class ResourceMachine implements Runnable {
		VendingMachine vendingMachine=null;
		String input;
		
		public ResourceMachine(VendingMachine vendingMachine,String input) {
			this.vendingMachine = vendingMachine;
			this.input = input;
		}
	
		@Override
		public void run() {
			this.vendingMachine.makeDemandedDrink(this.input);
		}
		
	}

	public void startMachine(Map<String, Object> mapOfBeverage, Map<String, Object> mapOfTotalQuantity, 
						int outlets, String[] userInput) {
		VendingMachine vendingMachine =  new VendingMachine(mapOfBeverage,mapOfTotalQuantity);
		int totalBeveragesWanted=userInput.length;
		outlets= 1;
		Thread[] threads = new Thread[outlets];
		ResourceMachine[] resourceMachine = new ResourceMachine[outlets];
		 for (int i = 0; i <= totalBeveragesWanted-outlets;) {
			 for(int j = 0;j<outlets;j++) {
				 resourceMachine[j] = new ResourceMachine(vendingMachine,userInput[i++]); 
				 threads[j] = new Thread(resourceMachine[j]);
			     threads[j].start();
			 }
		 }
	}
}