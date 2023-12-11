package inz23.stolmel.dataTypeClasses;

public class Product {

	private int id;
	private String name;
	private String price;

	public Product() {
		this.id = -1;
		this.name = "";
		this.price = "";
	}

	public Product(int id, String name, String price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPrice() {
		return price;
	}

	private void setId(int id) {
		this.id = id;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setPrice(String price) {
		this.price = price;
	}

	@Override
    public String toString() {
        return String.format("""
		{"id":"%d", "name":"%s", "price":"%s"}""", this.id, this.name, this.price);
    }
}