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
import facade.BuyerRestService;

@Transactional
@Component
public class BuyerRestServiceImpl implements BuyerRestService {

    @Autowired
    private ClientService clientService;
    
    @Autowired
    private ProductService productService;
    
    ModelMapper mapper = new ModelMapper();
    
    @Override
    public List<ProductDto> productDtoMapper(List<Product> products){
    	ModelMapper mapper = new ModelMapper();
    	List<ProductDto> productDtos = new LinkedList<ProductDto>();
    	for (Product product : products){
    		ProductDto productDto = mapper.map(product, ProductDto.class);    		
    		productDtos.add(productDto);    		
    	}
    	
    	return productDtos;
    }

    @Override
    public ClientDto getClient(String clientId) {
        if (clientService.isClientExist(Integer.valueOf(clientId))) {
        	ModelMapper mapper = new ModelMapper();
        	Client client = clientService.getClient(Integer.valueOf(clientId));
        	List<Product> products = client.getProducts();
        	ClientDto clientDto = mapper.map(client, ClientDto.class);
        	clientDto.setProductDtos(productDtoMapper(products));
            return clientDto;
        } else {
            return null;
        }

    }

    @Override
    public List<ClientDto> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        List<ClientDto> clientDtos = new LinkedList<ClientDto>();
        ModelMapper mapper = new ModelMapper();
        for(Client client: clients){
            List<Product> products = client.getProducts();
            ClientDto clientDto = mapper.map(client, ClientDto.class);
            clientDto.setProductDtos(productDtoMapper(products));
            clientDtos.add(clientDto);
        }
        return clientDtos;

    }

    @Override
    public Response deleteClient(String clientId) {
        if (clientService.isClientExist(Integer.valueOf(clientId))) {
        	List<Product> products = clientService.getClient(Integer.valueOf(clientId)).getProducts();
            for(Product product : products){
                product.getClients().remove(clientService.getClient(Integer.valueOf(clientId)));
            }
            clientService.deleteClient(Integer.valueOf(clientId));
            return Response.status(Response.Status.OK).build();
        } else {
            return null;
        }

    }

    @Override
    public Client addClient(ClientDto clientDto) {
    	List<Product> products = new ArrayList<Product>();
    	List<ClientDto> clientDtos = new ArrayList<ClientDto>();
    	clientDtos.add(clientDto);
    	if(clientDto.getProductDtos()!=null){
    		for(ProductDto productDto : clientDto.getProductDtos()){
    			productDto.setClientDtos(clientDtos);
    			Product product = mapper.map(productDto, Product.class);
    			productService.addProduct(product);
    			products.add(product);
    		}
    	}
    	Client client = mapper.map(clientDto, Client.class);
    	client.setProducts(products);               
        return clientService.addClient(client);
    }

	@Override
	public List<ClientDto> getProfitableClients() {
		List<Client> clients = clientService.getProfitableClients();
        List<ClientDto> clientDtos = new LinkedList<ClientDto>();
        ModelMapper mapper = new ModelMapper();
        for(Client client: clients){
            List<Product> products = client.getProducts();
            ClientDto clientDto = mapper.map(client, ClientDto.class);
            clientDto.setProductDtos(productDtoMapper(products));
            clientDtos.add(clientDto);
        }
        return clientDtos;			
	}

}
