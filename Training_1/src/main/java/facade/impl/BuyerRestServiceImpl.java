package facade.impl;

import dto.ClientDto;
import dto.ProductDto;
import entity.Client;
import entity.Product;
import facade.BuyerRestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import service.ClientService;

import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

@Transactional
@Component
public class BuyerRestServiceImpl implements BuyerRestService {

    @Autowired
    private ClientService clientService;
    
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
    public Response addClient(Client client) {
        clientService.addClient(client);
        return Response.status(Response.Status.CREATED).build();
    }

}
