package fixture;

import java.util.LinkedList;

import dto.ClientDto;
import dto.ProductDto;

public class ClientDtoFixture {
	
	public ClientDto simpleClientDto(){
		ClientDto clientDto = new ClientDto();
		clientDto.setId(1);
		clientDto.setName("Vitaliy");
		clientDto.setSurname("Kryzhalko");
		clientDto.setProfit(112.12);
		clientDto.setProductDtos(new LinkedList<ProductDto>());
		
		return clientDto;
	}

}
