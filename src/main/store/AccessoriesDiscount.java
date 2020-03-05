package store;

public class AccessoriesDiscount implements DiscountCalculator {

	@Override
	public float calculateDiscount(float itemAmount, OrderItem item) {
		float discount=0;
		if (itemAmount >= 100) 
			discount = itemAmount * 10 / 100;
		return discount;
	}

}
