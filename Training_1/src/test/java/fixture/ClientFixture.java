package fixture;

import java.util.LinkedList;

import entity.Client;
import entity.Product;

public class ClientFixture {
	
	public Client simpleClient(){
		Client client = new Client();
		client.setName("Vitaliy");
		client.setSurname("Kryzhalko");
		client.setProfit(112.12);
		client.setProducts(new LinkedList<Product>());
		
		return client;
	}

}
