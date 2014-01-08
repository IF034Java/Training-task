package fixture;

import java.util.LinkedList;

import dto.ClientDto;
import dto.ProductDto;

public class ProductDtoFixture {
	
	public ProductDto simpleProductDto(){
		ProductDto productDto = new ProductDto();
		productDto.setName("Bread");
		productDto.setPrice(4.55);
		productDto.setExpirationDate("25.12.2013");
		productDto.setClientDtos(new LinkedList<ClientDto>());
		
		return productDto;
	}

}
