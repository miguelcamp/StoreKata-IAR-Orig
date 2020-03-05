package store;

public class BikesDiscount implements DiscountCalculator {

	@Override
	public float calculateDiscount(float itemAmount, OrderItem item) {
		return itemAmount * 20 / 100;
	}

}
