package modal;

public class CliqProduct {

	private String brandName;
	private String productName;
	private String productCategory;
	private double tataCliqSellingPrice;
	private double originalMrp;
	private String type;

	public CliqProduct(String brand, String product, String category, double sellingPrice, double costPrice, String type) {
		
		this.brandName = brand;
		this.productName = product;
		this.productCategory = category;
		this.tataCliqSellingPrice = sellingPrice;
		this.originalMrp = costPrice;
		this.type = type;

	}

	public String getBrandName() {
		return brandName;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public double getTataCliqSellingPrice() {
		return tataCliqSellingPrice;
	}

	public double getOriginalMrp() {
		return originalMrp;
	}

	public String getType() {
		return type;
	}
	
	
	
}