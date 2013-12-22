package facade;

import dto.ClientDto;
import dto.ProductDto;
import entity.Client;
import entity.Product;

import javax.ws.rs.core.Response;
import java.util.List;

public interface BuyerRestService {
    List<ProductDto> productDtoMapper(List<Product> products);

    ClientDto getClient(String clientId);

    List<ClientDto> getAllClients();

    Response deleteClient(String clientId);

    Response addClient(Client client);
}
