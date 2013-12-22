package facade;

import dto.ClientDto;
import dto.ProductDto;
import entity.Client;
import entity.Product;

import javax.ws.rs.core.Response;
import java.util.List;

public interface StoreRestService {
    List<ClientDto> clientDtoMapper(List<Client> clients);

    ProductDto getProduct(String productId);

    List<ProductDto> getAllProducts();

    Response deleteProduct(String productId);

    Response addProduct(Product product);
}
