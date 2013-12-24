package facade.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import service.ClientService;
import service.ProductService;
import dto.ClientDto;
import dto.ProductDto;
import entity.Client;
import entity.Product;
import facade.StoreRestService;

@Transactional
@Component
public class StoreRestServiceImpl implements StoreRestService {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ClientService clientService;

    ModelMapper mapper = new ModelMapper();
    
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
        	List<Client> clients = productService.getProduct(Integer.valueOf(productId)).getClients();
            for(Client client : clients){
            	client.getProducts().remove(productService.getProduct(Integer.valueOf(productId)));                
            }
            productService.deleteProduct(Integer.valueOf(productId));
            return Response.status(Response.Status.OK).build();
        } else {
            return null;
        }
    }

    @Override
    public Product addProduct(ProductDto productDto) {
    	List<Client> clients = new ArrayList<Client>();
    	List<ProductDto> productDtos = new ArrayList<ProductDto>();
    	productDtos.add(productDto);
    	if(productDto.getClientDtos()!=null){
    		for (ClientDto clientDto: productDto.getClientDtos()){
    			clientDto.setProductDtos(productDtos);
    			Client client = mapper.map(clientDto, Client.class);
                clientService.addClient(client);
    			clients.add(client);
    		}
    	}
        Product product = mapper.map(productDto, Product.class);
    	product.setClients(clients);
        return productService.addProduct(product);
    }
}
