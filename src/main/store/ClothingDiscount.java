package store;

public class ClothingDiscount implements DiscountCalculator {

	@Override
	public float calculateDiscount(float itemAmount, OrderItem item) {
		float discount =0;
		if(item.getQuantity() > 2)
			discount = item.getProduct().getUnitPrice();
		return discount;
	}

}
