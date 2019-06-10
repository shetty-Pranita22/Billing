package com.billing.service;

import java.math.BigDecimal;
import java.util.List;

import com.billing.exception.NotInStockException;
import com.billing.model.Item;

public interface BillingTerminal {

	BigDecimal calculatePrice(int qty, String name);

	List<Item> getItemCodes();

	void editQuantity(List<Item> cartData);

	boolean checkAvailability(String pName, int pQty) throws NotInStockException;

}
