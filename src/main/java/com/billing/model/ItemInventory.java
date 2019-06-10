package com.billing.model;

import java.io.Serializable;

final public class ItemInventory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String itemCode;
	private final int quantity;

	public ItemInventory(String itemCode, int quantity) {
		super();
		this.itemCode = itemCode;
		this.quantity = quantity;
	}

	public String getItemCode() {
		return itemCode;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		return "ItemInventory [itemCode=" + itemCode + ", quantity=" + quantity + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemCode == null) ? 0 : itemCode.hashCode());
		result = prime * result + quantity;
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
		ItemInventory other = (ItemInventory) obj;
		if (itemCode == null) {
			if (other.itemCode != null)
				return false;
		} else if (!itemCode.equals(other.itemCode))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

}
