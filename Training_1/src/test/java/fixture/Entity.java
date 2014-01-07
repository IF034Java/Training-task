package fixture;

import java.util.LinkedList;

import dto.ClientDto;
import dto.ProductDto;
import entity.Client;
import entity.Product;

public class Entity {
	
	public Client getClient(){
		Client client = new Client();
		client.setName("Vitaliy");
		client.setSurname("Kryzhalko");
		client.setProfit(12.12);
		client.setProducts(new LinkedList<Product>());
		
		return client;
	}
	
	public ClientDto getClientDto(){
		ClientDto clientDto = new ClientDto();
		clientDto.setName("Vitaliy");
		clientDto.setSurname("Kryzhalko");
		clientDto.setProfit(12.12);
		clientDto.setProductDtos(new LinkedList<ProductDto>());
		
		return clientDto;
	}
	
	public Product getProduct(){
		Product product = new Product();
		product.setName("Bread");
		product.setPrice(4.55);
		product.setExpirationDate("25.12.2013");
		product.setClients(new LinkedList<Client>());
		
		return product;
	}
	
	public ProductDto getProductDto(){
		ProductDto productDto = new ProductDto();
		productDto.setName("Bread");
		productDto.setPrice(4.55);
		productDto.setExpirationDate("25.12.2013");
		productDto.setClientDtos(new LinkedList<ClientDto>());
		
		return productDto;
	}

}
