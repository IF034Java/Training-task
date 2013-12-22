package facade.impl;

import dto.ClientDto;
import dto.ProductDto;
import entity.Client;
import entity.Product;
import facade.StoreRestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import service.ProductService;

import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

@Transactional
@Component
public class StoreRestServiceImpl implements StoreRestService {

    @Autowired
    private ProductService productService;

    @Override
    public List<ClientDto> clientDtoMapper(List<Client> clients){
    	ModelMapper mapper = new ModelMapper();
    	List<ClientDto> clientDtos = new LinkedList<ClientDto>();
    	for (Client client: clients){
    		ClientDto clientDto = mapper.map(client, ClientDto.class);
    		clientDtos.add(clientDto);
    	}
    	
    	return clientDtos;
    }

    @Override
    public ProductDto getProduct(String productId) {
        if (productService.isProductExist(Integer.valueOf(productId))) {
        	ModelMapper mapper = new ModelMapper();
        	Product product = productService.getProduct((Integer.valueOf(productId)));
        	List<Client> clients = product.getClients();        
        	ProductDto productDto = mapper.map(product, ProductDto.class);
        	productDto.setClientDtos(clientDtoMapper(clients));
            return productDto;
        } else {
            return null;
        }
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productService.getAllProducts();
    	List<ProductDto> productDtos = new LinkedList<ProductDto>();
    	ModelMapper mapper = new ModelMapper();
    	for(Product product: products){
    		List<Client> clients = product.getClients();    		
    		ProductDto productDto = mapper.map(product, ProductDto.class);
    		productDto.setClientDtos(clientDtoMapper(clients));
    		productDtos.add(productDto);
    	}
        return productDtos;
    }

    @Override
    public Response deleteProduct(String productId) {
        if (productService.isProductExist(Integer.valueOf(productId))) {
            productService.deleteProduct(Integer.valueOf(productId));
            return Response.status(Response.Status.OK).build();
        } else {
            return null;
        }
    }

    @Override
    public Response addProduct(Product product) {
        productService.addProduct(product);
        return Response.status(Response.Status.CREATED).build();
    }


}
