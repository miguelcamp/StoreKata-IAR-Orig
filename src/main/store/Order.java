package store;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order {

	private static final int _MIN_FOR_CLOTHING_DISCOUNT = 2;
	private static final int _MIN_FOR_ACC_DISCOUNT = 100;
	private static final int _SHIPPING_COST = 15;
	private Customer customer;
	private Salesman salesman;
	private Date orderedOn;
	private String deliveryStreet;
	private String deliveryCity;
	private String deliveryCountry;
	private Set<OrderItem> items;

	public Order(Customer customer, Salesman salesman, String deliveryStreet, String deliveryCity, String deliveryCountry, Date orderedOn) {
		this.customer = customer;
		this.salesman = salesman;
		this.deliveryStreet = deliveryStreet;
		this.deliveryCity = deliveryCity;
		this.deliveryCountry = deliveryCountry;
		this.orderedOn = orderedOn;
		this.items = new HashSet<OrderItem>();
	}

	public Customer getCustomer() {
		return customer;
	}

	public Salesman getSalesman() {
		return salesman;
	}

	public Date getOrderedOn() {
		return orderedOn;
	}

	public String getDeliveryStreet() {
		return deliveryStreet;
	}

	public String getDeliveryCity() {
		return deliveryCity;
	}

	public String getDeliveryCountry() {
		return deliveryCountry;
	}

	public Set<OrderItem> getItems() {
		return items;
	}

	public float calculateTotal() {
		float baseTotal = 0;
		float tax=0;
		float shipping=0;
		baseTotal = calculateBaseTotal(baseTotal);
		tax = calculateTax(baseTotal);
		shipping = calculateShipping();
		return baseTotal + tax + shipping;
	}

	private float calculateShipping() {
		float shipping;
		shipping = _SHIPPING_COST;
		if (countryIsUSA())// shipping free
			shipping=0;
		return shipping;
	}

	private float calculateBaseTotal(float totalAllItems) {
		for (OrderItem item : items) {
			float totalPerItem=0;
			float itemAmount = item.getProduct().getUnitPrice() * item.getQuantity();
			totalPerItem = calculateDiscount(item, totalPerItem, itemAmount);
			totalAllItems += totalPerItem;
		}
		return totalAllItems;
	}

	private float calculateDiscount(OrderItem item, float totalPerItem, float itemAmount) {
		if (isAccessories(item)) {
			float accesoriesDiscount = 0;
			if (itemAmount >= _MIN_FOR_ACC_DISCOUNT) {
				accesoriesDiscount = itemAmount * 10 / 100;
			}
			totalPerItem = itemAmount - accesoriesDiscount;
		}
		if (isBikes(item)) {
			// 20% discount for Bikes
			totalPerItem = itemAmount - itemAmount * 20 / 100;
		}
		if (isClothing(item)) {
			float cloathingDiscount = 0;
			if (item.getQuantity() > _MIN_FOR_CLOTHING_DISCOUNT) {
				cloathingDiscount = item.getProduct().getUnitPrice();
			}
			totalPerItem = itemAmount - cloathingDiscount;
		}
		return totalPerItem;
	}

	private boolean isClothing(OrderItem item) {
		return item.getProduct().getCategory() == ProductCategory.Clothing;
	}

	private boolean isBikes(OrderItem item) {
		return item.getProduct().getCategory() == ProductCategory.Bikes;
	}

	private boolean isAccessories(OrderItem item) {
		return item.getProduct().getCategory() == ProductCategory.Accessories;
	}

	private boolean countryIsUSA() {
		return this.deliveryCountry == "USA";
	}

	private float calculateTax(float totalAllItems) {
		return totalAllItems * 5 / 100;
	}
}
