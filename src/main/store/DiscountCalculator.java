package store;

public interface DiscountCalculator {
	public float calculateDiscount(float itemAmount, OrderItem item);
}
