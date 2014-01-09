package fixture;

import java.util.LinkedList;

import entity.Client;
import entity.Product;

public class ProductFixture {
	
	public Product simpleProduct(){
		Product product = new Product();
		product.setId(1);
		product.setName("Bread");
		product.setPrice(4.55);
		product.setExpirationDate("25.12.2013");
		product.setClients(new LinkedList<Client>());
		
		return product;
	}

}
