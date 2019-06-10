package com.billing.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billing.model.Item;

public class ItemCodeMapper implements RowMapper<Item> {

	@Override
	public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Item item = new Item(rs.getString(1),0,new BigDecimal(0.0),new BigDecimal(0.0));	
		return item;
	}

}
