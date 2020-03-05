package store;

public class Product {

	/* The Name */
	public String name;

	/* The UnitPrice */
	public float unitPrice;

	/* The Category */
	public ProductCategory category;

	/* The Image */
	public ImageInfo image;

	/* The Category */
	public int unitsInStock;

	public Product(String name, float unitPrice, ProductCategory category, ImageInfo image) {
		this.name = name;
		this.unitPrice = unitPrice;
		this.category = category;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public ImageInfo getImage() {
		return image;
	}

	public int getUnitsInStock() {
		return unitsInStock;
	}

	public void setUnitsInStock(int unitsInStock) {
		this.unitsInStock = unitsInStock;
	}

	public String toXml() {
		return "<product>" + "<name>" + name + "</name>" + "<category>"
				+ category + "</category>" + "</product>";
	}

	boolean isAccessory() {
		return getCategory() == ProductCategory.Accessories;
	}

	boolean isClothing() {
		return getCategory() == ProductCategory.Clothing;
	}

	boolean isBike() {
		return getCategory() == ProductCategory.Bikes;
	}

	DiscountCalculator createDiscountCalculator(){
		DiscountCalculator discountCalculator = null;
		if(isAccessory()){
			discountCalculator = new AccessoriesDiscount();
		}
		if (isBike()) {
			discountCalculator = new BikesDiscount();
		}
		if (isClothing()) {
			discountCalculator = new ClothingDiscount();
		}
		
		return discountCalculator;
	}

}
