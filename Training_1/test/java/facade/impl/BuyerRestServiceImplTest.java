package facade.impl;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import service.ClientService;
import dto.ClientDto;
import dto.ProductDto;
import entity.Client;
import entity.Product;
import fixture.Entity;

@RunWith(MockitoJUnitRunner.class)
public class BuyerRestServiceImplTest {
	
	private Client client;
	private ClientDto clientDto;
	private Product product;
	private ProductDto productDto;
	private List<Client> clients;
	private List<ClientDto> clientDtos;
	private List<Product> products;
	private List<ProductDto> productDtos;
	
	@Mock
	private ClientService clientService;
	
	@InjectMocks
	private BuyerRestServiceImpl buyerRestServiceImpl;
	
	@Before
	public void initMocks(){
		MockitoAnnotations.initMocks(this);
		client = new Entity().getClient();
		clientDto = new Entity().getClientDto();
		product = new Entity().getProduct();
		productDto = new Entity().getProductDto();
		clients = new LinkedList<Client>();
		clients.add(client);
		clientDtos = new LinkedList<ClientDto>();
		clientDtos.add(clientDto);
		products = new LinkedList<Product>();
		products.add(product);
		productDtos = new LinkedList<ProductDto>();
		productDtos.add(productDto);
	}
	
	@Test
//	public List<ProductDto> productDtoMapper(List<Product> products)
	public void productDtoMapper(){		
		Assert.assertTrue(productDtos.size()==buyerRestServiceImpl.productDtoMapper(products).size());		
		Assert.assertTrue(productDtos.containsAll(buyerRestServiceImpl.productDtoMapper(products)));		
	}
	
	@Test
//	public ClientDto getClient(String clientId)
	public void getExistingClientTest(){				
		Mockito.when(clientService.isClientExist(Integer.valueOf("1"))).thenReturn(true);
		Mockito.when(clientService.getClient(Integer.valueOf(1))).thenReturn(client);		
		Assert.assertEquals(clientDto, buyerRestServiceImpl.getClient("1"));
		
	}
	
	@Test
//	public ClientDto getClient(String clientId)
	public void getNonExistingClientTest(){						
		Mockito.when(clientService.isClientExist(Mockito.anyInt())).thenReturn(false);				
		Assert.assertNull(buyerRestServiceImpl.getClient("1"));		
	}
	
	@Test
//	public List<ClientDto> getAllClients() {
	public void getAllClients(){
		Mockito.when(clientService.getAllClients()).thenReturn(clients);
		Assert.assertTrue(clientDtos.size()==buyerRestServiceImpl.getAllClients().size());		
		Assert.assertTrue(clientDtos.containsAll(buyerRestServiceImpl.getAllClients()));		
	}
	
	@Test
//	public Response deleteClient(String clientId)
	public void deleteExistingClientTest(){		
		int status = 200;		
		Mockito.when(clientService.isClientExist(Integer.valueOf("1"))).thenReturn(true);
		Mockito.when(clientService.getClient(Integer.valueOf("1"))).thenReturn(client);		
		Assert.assertEquals(status, buyerRestServiceImpl.deleteClient("1").getStatus());					
	}
	
	@Test
//	public Response deleteClient(String clientId)
	public void deleteNonExistingClientTest(){		
		Mockito.when(clientService.isClientExist(Integer.valueOf("1"))).thenReturn(false);						
		Assert.assertNull(buyerRestServiceImpl.deleteClient("1"));		
	}
	
	@Test
//	public Client addClient(ClientDto clientDto)
	public void addClientTest(){		
		Mockito.when(clientService.addClient(client)).thenReturn(client);
		Assert.assertEquals(client, buyerRestServiceImpl.addClient(clientDto));
	}
	
	@Test
//	public List<ClientDto> getProfitableClients()
	public void getProfitableClientsTest() {
		Mockito.when(clientService.getProfitableClients()).thenReturn(clients);
		Assert.assertEquals(clientDtos, buyerRestServiceImpl.getProfitableClients());
		
	}

}
