package com.billing.model;

import java.io.Serializable;
import java.math.BigDecimal;

final public class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String itemName;
	private final int qty;
	private final BigDecimal price;
	private final BigDecimal bulkPrice;

	public Item(String itemName, int qty, BigDecimal price, BigDecimal bulkPrice) {
		super();
		this.itemName = itemName;
		this.qty = qty;
		this.price = price;
		this.bulkPrice = bulkPrice;
	}

	public BigDecimal getBulkPrice() {
		return bulkPrice;
	}

	public String getItemName() {
		return itemName;
	}

	public int getQty() {
		return qty;
	}

	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "Item [itemName=" + itemName + ", qty=" + qty + ", price=" + price + ", bulkPrice=" + bulkPrice + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bulkPrice == null) ? 0 : bulkPrice.hashCode());
		result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + qty;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (bulkPrice == null) {
			if (other.bulkPrice != null)
				return false;
		} else if (!bulkPrice.equals(other.bulkPrice))
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (qty != other.qty)
			return false;
		return true;
	}

}
