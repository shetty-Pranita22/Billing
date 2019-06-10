package com.billing.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billing.model.ItemInventory;

public class InventoryRowByNameMapper implements RowMapper<ItemInventory> {

	@Override
	public ItemInventory mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		ItemInventory newItem = new ItemInventory(rs.getString(1), rs.getInt(2));
		return newItem;
	}

}
