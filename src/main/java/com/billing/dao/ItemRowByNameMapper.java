package com.billing.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billing.model.Item;

public class ItemRowByNameMapper implements RowMapper<Item> {

	@Override
	public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Item obtainedItem = new Item(rs.getString(1), rs.getInt(3), new BigDecimal(rs.getDouble(4)), new BigDecimal(rs.getDouble(2)));
		return obtainedItem;
	}

}
