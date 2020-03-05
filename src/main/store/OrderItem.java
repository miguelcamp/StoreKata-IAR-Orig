package store;

public class OrderItem {
	
	private Product product;
	private int quantity;

	/*
	 * Order Item Constructor
	 */
	public OrderItem(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}
	
	float calculateTotalForItem() {
		float itemAmount = calculateBaseAmountPerItem();
		float discount=0;
		DiscountCalculator discountCalculator = createDiscountCalculator();
		discount = discountCalculator.calculateDiscount(itemAmount, this);
		return itemAmount - discount;
	}

	float calculateBaseAmountPerItem() {
		return getProduct().getUnitPrice() * getQuantity();
	}


	private DiscountCalculator createDiscountCalculator(){
		DiscountCalculator discountCalculator = null;
		if(getProduct().isAccessory()){
			discountCalculator = new AccessoriesDiscount();
		}
		if (getProduct().isBike()) {
			discountCalculator = new BikesDiscount();
		}
		if (getProduct().isClothing()) {
			discountCalculator = new ClothingDiscount();
		}
		
		return discountCalculator;
	}
	
	
}
