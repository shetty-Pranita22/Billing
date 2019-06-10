package com.billing.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billing.dao.BillingDAO;
import com.billing.dao.ItemInventoryDAO;
import com.billing.exception.NotInStockException;
import com.billing.model.Item;
import com.billing.model.ItemInventory;

@Service
public class BillingTerminalImpl implements BillingTerminal {

	@Autowired
	private final BillingDAO billingDAO;

	@Autowired
	private final ItemInventoryDAO inventoryDAO;

	public BillingTerminalImpl(BillingDAO billingDAO2, ItemInventoryDAO itemInventoryDAO) {
		// TODO Auto-generated constructor stub
		billingDAO = (BillingDAO) billingDAO2;
		inventoryDAO = (ItemInventoryDAO) itemInventoryDAO;
	}

	public BigDecimal calculatePrice(int qty, String name) {
		// TODO Auto-generated method stub
		Item items = billingDAO.getItemRowByName(name);
		if (items.getBulkPrice().doubleValue() != 0) {
			return (items.getBulkPrice().multiply(new BigDecimal(qty / items.getQty()))
					.add(items.getPrice().multiply(new BigDecimal(qty % items.getQty()))));
		} else {
			return items.getPrice().multiply(new BigDecimal(qty));
		}
	}

	@Override
	public List<Item> getItemCodes() {
		// TODO Auto-generated method stub
		ArrayList<Item> itemCode = (ArrayList<Item>) billingDAO.getItemCodes();
		return itemCode;
	}

	@Override
	public void editQuantity(List<Item> cartData) {
		// TODO Auto-generated method stub
		for (Item item : cartData) {
			ItemInventory eachItem = inventoryDAO.getItemInventoryRowByName(item.getItemName());
			int upDatedQty = eachItem.getQuantity() - item.getQty();
			ItemInventory uItem = new ItemInventory(item.getItemName(), upDatedQty);
			inventoryDAO.updateQty(uItem);
		}

	}

	@Override
	public boolean checkAvailability(String pName, int pQty) throws NotInStockException {
		// TODO Auto-generated method stub
		ItemInventory item = inventoryDAO.getItemInventoryRowByName(pName);
		if (pQty < item.getQuantity()) {
			return true;
		} else {
			throw new NotInStockException("" + item.getQuantity());
		}
	}

}
