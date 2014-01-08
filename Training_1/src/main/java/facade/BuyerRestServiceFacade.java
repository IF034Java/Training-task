package facade;

import java.util.List;

import javax.ws.rs.core.Response;

import dto.ClientDto;
import dto.ProductDto;
import entity.Client;
import entity.Product;

public interface BuyerRestServiceFacade {
//    List<ProductDto> productDtoMapper(List<Product> products);

    ClientDto getClient(String clientId);

    List<ClientDto> getAllClients();

    Response deleteClient(String clientId);

    Client addClient(ClientDto clientDto);
    
    List<ClientDto> getProfitableClients();
}
