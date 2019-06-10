package com.billing.dao;

import com.billing.model.ItemInventory;

public interface ItemInventoryDAO {

	public ItemInventory getItemInventoryRowByName(String name);

	public void updateQty(ItemInventory uItem);
}
