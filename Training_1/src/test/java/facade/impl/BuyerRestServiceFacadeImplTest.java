package facade.impl;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import service.ClientService;
import service.ProductService;
import dto.ClientDto;
import dto.ProductDto;
import entity.Client;
import entity.Product;
import fixture.ClientDtoFixture;
import fixture.ClientFixture;
import fixture.ProductDtoFixture;
import fixture.ProductFixture;

@RunWith(MockitoJUnitRunner.class)
public class BuyerRestServiceFacadeImplTest {
	
	private static Client client;
	private static ClientDto clientDto;
	private static Product product;
	private static ProductDto productDto;
	private static List<Client> clients;
	private static List<ClientDto> clientDtos;
	private static List<Product> products;
	private static List<ProductDto> productDtos;
	private boolean clientExist;	
	
	@Mock
	private static ClientService clientService;
	
	@Mock
	private static ProductService productService;
	
	@InjectMocks
	private BuyerRestServiceFacadeImpl buyerRestServiceFacadeImpl;		
	
	@BeforeClass
	public static void initInputs(){
		client = new ClientFixture().simpleClient();
		clientDto = new ClientDtoFixture().simpleClientDto();
		product = new ProductFixture().simpleProduct();
		productDto = new ProductDtoFixture().simpleProductDto();
		clients = new LinkedList<Client>();
		clients.add(client);
		clientDtos = new LinkedList<ClientDto>();
		clientDtos.add(clientDto);
		products = new LinkedList<Product>();
		products.add(product);
		productDtos = new LinkedList<ProductDto>();
		productDtos.add(productDto);		
	}
	
	@Before
	public void initMocks(){
		MockitoAnnotations.initMocks(this);
		clientExist = clientService.isClientExist(Integer.valueOf("1"));
	}
	
	
	@Test
//	public ClientDto getClient(String clientId)
	public void getExistingClientTest(){				
		Mockito.when(clientExist).thenReturn(true);		
		Mockito.when(clientService.getClient(Integer.valueOf(1))).thenReturn(client);
		ClientDto actualClientDto = buyerRestServiceFacadeImpl.getClient("1");		
		
		Assert.assertEquals(client.getId(), actualClientDto.getId());
		Assert.assertEquals(client.getName(), actualClientDto.getName());
		Assert.assertEquals(client.getSurname(), actualClientDto.getSurname());
		Assert.assertEquals(client.getProfit(), actualClientDto.getProfit());
		
	}
	
	@Test
//	public ClientDto getClient(String clientId)
	public void getNonExistingClientTest(){						
		Mockito.when(clientService.isClientExist(Mockito.anyInt())).thenReturn(false);				
		Assert.assertNull(buyerRestServiceFacadeImpl.getClient("1"));		
	}
	
	@Test
//	public List<ClientDto> getAllClients() {
	public void getAllClients(){
		Mockito.when(clientService.getAllClients()).thenReturn(clients);
		List<ClientDto> actualClientDtos = buyerRestServiceFacadeImpl.getAllClients();
		
		Assert.assertTrue(clients.size()==actualClientDtos.size());						
		for (int i = 0; i<actualClientDtos.size(); i++){
			Assert.assertEquals(clients.get(i).getId(), actualClientDtos.get(i).getId());
			Assert.assertEquals(clients.get(i).getName(), actualClientDtos.get(i).getName());
			Assert.assertEquals(clients.get(i).getSurname(), actualClientDtos.get(i).getSurname());
			Assert.assertEquals(clients.get(i).getProfit(), actualClientDtos.get(i).getProfit());
		}
	}
	
	@Test
//	public Response deleteClient(String clientId)
	public void deleteExistingClientTest(){		
		int status = 200;		
		Mockito.when(clientExist).thenReturn(true);
		Mockito.when(clientService.getClient(Integer.valueOf("1"))).thenReturn(client);		
		Assert.assertEquals(status, buyerRestServiceFacadeImpl.deleteClient("1").getStatus());					
	}
	
	@Test
//	public Response deleteClient(String clientId)
	public void deleteNonExistingClientTest(){		
		Mockito.when(clientExist).thenReturn(false);						
		Assert.assertNull(buyerRestServiceFacadeImpl.deleteClient("1"));		
	}
	
	@Test
//	public Client addClient(ClientDto clientDto)
	public void addClientTest(){		
		Mockito.when(clientService.addClient(client)).thenReturn(client);
		Mockito.when(productService.addProduct(product)).thenReturn(product);
		Client actualClient = buyerRestServiceFacadeImpl.addClient(clientDto);		
		
		Assert.assertEquals(clientDto.getId(), actualClient.getId());
		Assert.assertEquals(clientDto.getName(), actualClient.getName());
		Assert.assertEquals(clientDto.getSurname(), actualClient.getSurname());
		Assert.assertEquals(clientDto.getProfit(), actualClient.getProfit());
	}
	
	@Test
//	public List<ClientDto> getProfitableClients()
	public void getProfitableClientsTest() {
		Mockito.when(clientService.getProfitableClients()).thenReturn(clients);
		List<ClientDto> actualProfitableClientDtos = buyerRestServiceFacadeImpl.getProfitableClients();		
			
			Assert.assertTrue(clients.size()==actualProfitableClientDtos.size());
			for(ClientDto clientDto : actualProfitableClientDtos){
				Assert.assertTrue(clientDto.getProfit()>100);
			}				
	}
}
