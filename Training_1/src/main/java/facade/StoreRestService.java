package facade;

import java.util.List;

import javax.ws.rs.core.Response;

import dto.ClientDto;
import dto.ProductDto;
import entity.Client;
import entity.Product;

public interface StoreRestService {
    List<ClientDto> clientDtoMapper(List<Client> clients);

    ProductDto getProduct(String productId);

    List<ProductDto> getAllProducts();

    Response deleteProduct(String productId);

    Product addProduct(ProductDto productDto);
}
