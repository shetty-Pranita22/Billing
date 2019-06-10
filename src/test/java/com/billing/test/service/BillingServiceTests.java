package com.billing.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import org.junit.Test;
import com.billing.dao.BillingDAO;
import com.billing.dao.ItemInventoryDAO;
import com.billing.exception.NotInStockException;
import com.billing.model.Item;
import com.billing.model.ItemInventory;
import com.billing.service.BillingTerminal;
import com.billing.service.BillingTerminalImpl;

//@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
public class BillingServiceTests {

	@Test
	public void testCalculatePrice() {
		// given
		BigDecimal expected = new BigDecimal(21.25);
		Item expectedItem = new Item("3-Q", 0, new BigDecimal(4.25), new BigDecimal(0.0));
		String itemName = "3-Q";
		int qty = 5;

		BillingDAO billingDAO = mock(BillingDAO.class);

		BillingTerminal testClass = new BillingTerminalImpl(billingDAO, null);
		// when
		when(billingDAO.getItemRowByName(itemName)).thenReturn(expectedItem);
		BigDecimal actual = testClass.calculatePrice(qty, itemName);

		// then
		assertEquals(expected, actual);

	}

	@Test
	public void testGetItemCodes() {
		// given
		List<Item> expected = new ArrayList<>();
		expected.add(new Item("3-Q", 0, new BigDecimal(4.25), new BigDecimal(0.0)));
		expected.add(new Item("45K11", 6, new BigDecimal(1.0), new BigDecimal(5.0)));
		List<Item> expectedItem = new ArrayList<>();
		expectedItem.add(new Item("3-Q", 0, new BigDecimal(4.25), new BigDecimal(0.0)));
		expectedItem.add(new Item("45K11", 6, new BigDecimal(1.0), new BigDecimal(5.0)));

		BillingDAO billingDAO = mock(BillingDAO.class);
		BillingTerminal testClass = new BillingTerminalImpl(billingDAO, null);
		// when
		when(billingDAO.getItemCodes()).thenReturn(expectedItem);
		List<Item> actual = testClass.getItemCodes();

		// then
		assertEquals(expected, actual);

	}

	@Test
	public void testCheckAvailability() throws NotInStockException {

		// given
		Boolean expected = true;
		ItemInventory expectedItem = new ItemInventory("3-Q", 50);
		String productName = "3-Q";
		int productQuantity = 10;

		ItemInventoryDAO itemInventoryDAO = mock(ItemInventoryDAO.class);
		BillingTerminal testClass = new BillingTerminalImpl(null, itemInventoryDAO);

		// when
		Boolean actual = false;
		try {
			when(itemInventoryDAO.getItemInventoryRowByName(productName)).thenReturn(expectedItem);
			actual = testClass.checkAvailability(productName, productQuantity);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

		// then
		assertEquals(expected, actual);

	}

	@Test(expected = NotInStockException.class)
	public void testCheckAvailabilityEXception() throws NotInStockException {
		
		// given
				//Boolean expected = true;
				//ItemInventory expectedItem = new ItemInventory("3-Q", 10);
				String productName = "3-Q";
				int productQuantity = 50;

				ItemInventoryDAO itemInventoryDAO = mock(ItemInventoryDAO.class);
				BillingTerminal testClass = new BillingTerminalImpl(null, itemInventoryDAO);

				// when
				//Boolean actual = false;
		
				when(itemInventoryDAO.getItemInventoryRowByName(productName)).thenThrow(new NotInStockException());
				testClass.checkAvailability(productName, productQuantity);
				
	  
	}
	
	@Test
	public void testEditQuantity() {
		// given
		List<Item> cartData = new ArrayList<>();
		cartData.add(new Item("3-Q", 6, new BigDecimal(0.0), new BigDecimal(0.0)));

		ItemInventory eachItem = new ItemInventory("3-Q", 10);

		ItemInventoryDAO itemInventoryDAO = mock(ItemInventoryDAO.class);

		BillingTerminal testClass = new BillingTerminalImpl(null, itemInventoryDAO);

		// when
		when(itemInventoryDAO.getItemInventoryRowByName("3-Q")).thenReturn(eachItem);
		testClass.editQuantity(cartData);

		// then
		verify(itemInventoryDAO).updateQty(new ItemInventory("3-Q", 4));

	}

}
