package com.example.demo.service;

import java.util.Map;

import org.springframework.stereotype.Service;


@Service
public interface CoffeeMachineService {

	public void startMachine(Map<String, Object> mapOfBeverage, Map<String, Object> mapOfTotalQuantity, int outlets, String[] userInput);
//	void startMachine(Map<String, Object> mapOfBeverages, Map<String, Object> mapOfTotalQuantity,
//			Integer numberOfOutlets, String[] userInput);

}
