package com.billing.test.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.billing.dao.InventoryRowByNameMapper;
import com.billing.dao.ItemInventoryDAO;
import com.billing.dao.ItemInventoryDAOImpl;
import com.billing.model.ItemInventory;

public class ItemInventoryDAOTest {

	@Test
	public void testGetRowByItemName() {
		// given
		ItemInventory expected = new ItemInventory("A1", 10);

		// when
		ItemInventory expectedItem = new ItemInventory("A1", 10);
		String query = "SELECT * FROM itemList WHERE itemName = ?";
		String name = "A1";
		Object search[] = { name };
		JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
		InventoryRowByNameMapper inventoryRowMapper = mock(InventoryRowByNameMapper.class);
		ItemInventoryDAO testClass = new ItemInventoryDAOImpl(jdbcTemplate, inventoryRowMapper);

		when(jdbcTemplate.queryForObject(query, search, inventoryRowMapper)).thenReturn(expectedItem);
		ItemInventory actual = testClass.getItemInventoryRowByName(name);

		// then
		assertEquals(expected, actual);
	}

	@Test
	public void testupdateQty() {
		// given
		ItemInventory editData = new ItemInventory("3-Q", 10);

		String query = "update itemList set Qty=? where itemName=?";
		Object[] inventoryItems = { editData.getQuantity(), editData.getItemCode() };

		JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

		ItemInventoryDAO testClass = new ItemInventoryDAOImpl(jdbcTemplate, null);

		// when
		testClass.updateQty(editData);

		// then
		verify(jdbcTemplate).update(query, inventoryItems);

	}

}
