package com.billing.test.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.billing.dao.BillingDAO;
import com.billing.dao.BillingDAOImpl;
import com.billing.dao.ItemCodeMapper;
import com.billing.dao.ItemRowByNameMapper;
import com.billing.model.Item;

public class BillingDAOTest {

	@Test
	public void testGetItemCodes() {
		// given
		List<Item> expected = new ArrayList<>();
		expected.add(new Item("3-Q", 0, new BigDecimal(4.25), new BigDecimal(0.0)));
		expected.add(new Item("45K11", 6, new BigDecimal(1.0), new BigDecimal(5.0)));

		List<Item> expectedItem = new ArrayList<>();
		expectedItem.add(new Item("3-Q", 0, new BigDecimal(4.25), new BigDecimal(0.0)));
		expectedItem.add(new Item("45K11", 6, new BigDecimal(1.0), new BigDecimal(5.0)));
		String query = "select itemCode from Item_Data";
		JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
		ItemCodeMapper itemMapper = mock(ItemCodeMapper.class);
		BillingDAO testClass = new BillingDAOImpl(jdbcTemplate, itemMapper, null);

		// when

		when(jdbcTemplate.query(query, itemMapper)).thenReturn(expectedItem);
		List<Item> actual = testClass.getItemCodes();
		System.out.println(actual);

		// then
		assertEquals(expected, actual);

	}

	@Test
	public void testGetRowByItemName() {
		// given
		Item expected = new Item("A1", 3, new BigDecimal(1.25), new BigDecimal(3.0));

		// when
		Item expectedItem = new Item("A1", 3, new BigDecimal(1.25), new BigDecimal(3.0));
		String query = "SELECT * FROM Item_Data WHERE itemCode = ?";
		String name = "A1";
		Object search[] = { name };
		JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
		ItemRowByNameMapper rowMapper = mock(ItemRowByNameMapper.class);
		BillingDAO testClass = new BillingDAOImpl(jdbcTemplate, null, rowMapper);

		when(jdbcTemplate.queryForObject(query, search, rowMapper)).thenReturn(expectedItem);
		Item actual = testClass.getItemRowByName(name);

		// then
		assertEquals(expected, actual);
	}

}
