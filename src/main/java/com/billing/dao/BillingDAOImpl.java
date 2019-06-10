package com.billing.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.billing.model.Item;

@Repository
public class BillingDAOImpl implements BillingDAO {

	@Autowired
	private final JdbcTemplate jdbcTemplate;
	
	private final ItemRowByNameMapper rowItemMapper;
	
	private final ItemCodeMapper itemCodeMapper;

	public BillingDAOImpl(JdbcTemplate jdbcTemplate2, ItemCodeMapper itemMapper, ItemRowByNameMapper itemRowMapper) {
		// TODO Auto-generated constructor stub
		jdbcTemplate = jdbcTemplate2;
		itemCodeMapper = itemMapper;
		rowItemMapper = itemRowMapper;
	}

	public Item getItemRowByName(String name) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM Item_Data WHERE itemCode = ?";
		Object search[] = { name };
		Item newItem = jdbcTemplate.queryForObject(query, search, rowItemMapper);
		return newItem;
	}

	public List<Item> getItemCodes() {
		String query = "select itemCode from Item_Data";
		List<Item> cartList = jdbcTemplate.query(query, itemCodeMapper);
		return cartList;
	}

}
