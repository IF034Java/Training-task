package facade;

import java.util.List;

import javax.ws.rs.core.Response;

import dto.ProductDto;
import entity.Product;

public interface StoreRestServiceFacade {    

    ProductDto getProduct(String productId);

    List<ProductDto> getAllProducts();

    Response deleteProduct(String productId);

    Product addProduct(ProductDto productDto);
}
