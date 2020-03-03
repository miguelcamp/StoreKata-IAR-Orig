package store;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order {

	private static final int _ACCESSORY_DISCOUNT = 10;
	private static final int _BIKE_DISCOUNT = 20;
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
		float totalItems = calculateTotalForItems();
		float tax = calculateTax(totalItems);
		float shipping = calculateShipping();
		return totalItems + tax + shipping;
	}

	private float calculateShipping() {
		float shipping = _SHIPPING_COST;
		if (countryIsUSA())
			shipping = 0;
		return shipping;
	}

	private boolean countryIsUSA() {
		return this.deliveryCountry == "USA";
	}

	private float calculateTax(float totalItems) {
		return totalItems * 5 / 100;
	}

	private float calculateTotalForItems() {
		float totalItems=0;
		for (OrderItem item : items) {
			float totalPerItem = calculateItemTotal(item);
			totalItems += totalPerItem;
		}
		return totalItems;
	}

	private float calculateBaseAmountPerItem(OrderItem item) {
		return item.getProduct().getUnitPrice() * item.getQuantity();
	}

	private float calculateItemTotal(OrderItem item) {
		float itemAmount = calculateBaseAmountPerItem(item);
		itemAmount -= calculateDiscount(item, itemAmount);
		return itemAmount;
	}

	private float calculateDiscount(OrderItem item, float itemAmount) {
		float discount = 0;
		if (isAccessory(item) && itemAmount >= 100) 
				discount = calculatePercentageDiscount(itemAmount, _ACCESSORY_DISCOUNT);
		if (isBike(item))
			discount = calculatePercentageDiscount(itemAmount, _BIKE_DISCOUNT);
		if (isClothing(item) && item.getQuantity() > 2) 
			discount = item.getProduct().getUnitPrice();
		return discount;
	}

	private float calculatePercentageDiscount(float itemAmount, int percentage) {
		return itemAmount * percentage / 100;
	}

	private boolean isClothing(OrderItem item) {
		return item.getProduct().getCategory() == ProductCategory.Clothing;
	}

	private boolean isBike(OrderItem item) {
		return item.getProduct().getCategory() == ProductCategory.Bikes;
	}

	private boolean isAccessory(OrderItem item) {
		return item.getProduct().getCategory() == ProductCategory.Accessories;
	}
}
