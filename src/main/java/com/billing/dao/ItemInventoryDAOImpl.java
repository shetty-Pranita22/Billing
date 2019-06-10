package com.billing.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.billing.model.ItemInventory;

@Repository
public class ItemInventoryDAOImpl implements ItemInventoryDAO {

	@Autowired
	private final JdbcTemplate jdbcTemplate;
	
	private final InventoryRowByNameMapper inventoryMapper;

	public ItemInventoryDAOImpl(JdbcTemplate jdbcTemplate2, InventoryRowByNameMapper inventoryRowMapper) {
		// TODO Auto-generated constructor stub
		jdbcTemplate = jdbcTemplate2;
		inventoryMapper = inventoryRowMapper;
	}

	public ItemInventory getItemInventoryRowByName(String name) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM itemList WHERE itemName = ?";
		Object search[] = { name };
		ItemInventory newInventoryItem = jdbcTemplate.queryForObject(query, search, inventoryMapper);
		return newInventoryItem;
	}

	@Transactional
	public void updateQty(ItemInventory uItem) {
		// TODO Auto-generated method stub
		String query = "update itemList set Qty=? where itemName=?";
		Object[] inventoryItems = { uItem.getQuantity(), uItem.getItemCode() };
		jdbcTemplate.update(query, inventoryItems);

	}
}
