package com.billing.dao;

import java.util.List;

import com.billing.model.Item;

public interface BillingDAO {
	public Item getItemRowByName(String name);

	public List<Item> getItemCodes();
}
