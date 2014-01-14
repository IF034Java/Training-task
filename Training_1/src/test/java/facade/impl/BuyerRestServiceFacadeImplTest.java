package facade.impl;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import service.ClientService;
import service.ProductService;
import dto.ClientDto;
import dto.ProductDto;
import entity.Client;
import entity.Product;

@RunWith(MockitoJUnitRunner.class)
public class BuyerRestServiceFacadeImplTest {
	

	private static Client client;
	private static ClientDto clientDto;	
	private static List<Client> clients;			
	
	private static final double CLIENT_PROFIT = 112.12;
	private static final String CLIENT_SURNAME = "Kryzhalko";
	private static final String CLIENT_NAME = "Vitaliy";
	private static Integer existingId = 1;
	private static Integer notExistingId = -1;
	
	@Mock
	private static ClientService clientService;
	
	@Mock
	private static ProductService productService;
	
	@InjectMocks
	private BuyerRestServiceFacadeImpl buyerRestServiceFacadeImpl;		
	
	@BeforeClass
	public static void initInputs(){		
		client = simpleClient();
		clients = clientsList();											
		clientDto = simpleClientDto();		
	}
	
	
	@Before
	public void initMocks(){
		MockitoAnnotations.initMocks(this);
		mockClientService();
	}
		
	private void mockClientService(){
		Mockito.when(clientService.isExist(existingId)).thenReturn(true);
		Mockito.when(clientService.isExist(notExistingId)).thenReturn(false);
		Mockito.when(clientService.get(existingId)).thenReturn(client);
		Mockito.when(clientService.getAll()).thenReturn(clients);
		Mockito.when(clientService.getProfitableClients()).thenReturn(clients);
		Mockito.when(clientService.add(Mockito.any(Client.class))).thenAnswer(new Answer<Client>() {
            @Override
            public Client answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Client) args[0];
            }
        });		
	}
	
	@Test
//	public ClientDto getClient(String clientId)
	public void getExistingClientTest(){		
		ClientDto actualClientDto = buyerRestServiceFacadeImpl.getClient("1");		
		Assert.assertEquals(CLIENT_NAME, actualClientDto.getName());
		Assert.assertEquals(CLIENT_SURNAME, actualClientDto.getSurname());
		Assert.assertEquals(CLIENT_PROFIT, actualClientDto.getProfit());
		Assert.assertEquals(existingId, actualClientDto.getId());
		List<ProductDto> productDtos = actualClientDto.getProductDtos();
		int i=0;
		for (ProductDto productDto : productDtos) {						
			Assert.assertEquals("Milk" + i, productDto.getName());
			Assert.assertEquals(8.13*i, productDto.getPrice());
			Assert.assertEquals("30.03.14", productDto.getExpirationDate());
			i++;
		}					
	}
	
	@Test
//	public ClientDto getClient(String clientId)
	public void getNonExistingClientTest(){						
		Assert.assertNull(buyerRestServiceFacadeImpl.getClient("-1"));		
	}
	
	@Test
//	public List<ClientDto> getAllClients() {
	public void getAllClients(){
		List<ClientDto> actualClientDtos = buyerRestServiceFacadeImpl.getAllClients();														
		int i=0;
		for (ClientDto dto: actualClientDtos){
			Assert.assertEquals("Vasyl"+ i, dto.getName());
			Assert.assertEquals("Vasylchenko"+ 2*i, dto.getSurname());
			Assert.assertEquals((double)(200+i), dto.getProfit());
			i++;
			int j=0;
			for(ProductDto productDto: dto.getProductDtos()){
				Assert.assertEquals("Milk" + j, productDto.getName());
				Assert.assertEquals(8.13*j, productDto.getPrice());
				Assert.assertEquals("30.03.14", productDto.getExpirationDate());
				j++;
			}
		}
	}
	
	@Test
//	public Response deleteClient(String clientId)
	public void deleteExistingClientTest(){		
		Response response = buyerRestServiceFacadeImpl.deleteClient(client.getId().toString());
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());					
	}
	
	@Test
//	public Response deleteClient(String clientId)
	public void deleteNonExistingClientTest(){		
		Assert.assertNull(buyerRestServiceFacadeImpl.deleteClient("-1"));		
	}
	
	@Test
//	public Client addClient(ClientDto clientDto)
	public void addClientTest(){		
		Client actualClient = buyerRestServiceFacadeImpl.addClient(clientDto);				
		Assert.assertEquals(CLIENT_NAME, actualClient.getName());
		Assert.assertEquals(CLIENT_SURNAME, actualClient.getSurname());
		Assert.assertEquals(CLIENT_PROFIT, actualClient.getProfit());
		Assert.assertEquals(existingId, actualClient.getId());
		List<Product> products = actualClient.getProducts();
		int i=0;
		for (Product product : products) {						
			Assert.assertEquals("Milk" + i, product.getName());
			Assert.assertEquals(8.13*i, product.getPrice());
			Assert.assertEquals("30.03.14", product.getExpirationDate());
			i++;
		}									
	}
	
	@Test
//	public List<ClientDto> getProfitableClients()
	public void getProfitableClientsTest() {		
		List<ClientDto> actualProfitableClientDtos = buyerRestServiceFacadeImpl.getProfitableClients();
		int i=0;
		for (ClientDto dto: actualProfitableClientDtos){
			Assert.assertEquals("Vasyl"+ i, dto.getName());
			Assert.assertEquals("Vasylchenko"+ 2*i, dto.getSurname());
			Assert.assertEquals((double)(200+i), dto.getProfit());
			i++;
			int j=0;
			for(ProductDto productDto: dto.getProductDtos()){
				Assert.assertEquals("Milk" + j, productDto.getName());
				Assert.assertEquals(8.13*j, productDto.getPrice());
				Assert.assertEquals("30.03.14", productDto.getExpirationDate());
				j++;
			}
		}
	}		
	
	private static List<Client> clientsList(){
		List<Client> clients = new LinkedList<Client>();
		for (int i = 0; i < 3; i++) {
			Client client = new Client();
			client.setName("Vasyl"+ i);
			client.setSurname("Vasylchenko" + 2*i);
			client.setProfit((double)(200+i));
			client.setProducts(new LinkedList<Product>());
			clients.add(client);
		}		
		return clients;
	}
	
	private static List<Product> productsList(){
		List<Product> products = new LinkedList<Product>();
		for (int i = 0; i < 3; i++) {
			Product product = new Product();
			product.setName("Milk" + i);
			product.setPrice(8.13*i);
			product.setExpirationDate("30.03.14");
			product.setClients(new LinkedList<Client>());
			products.add(product);			
		} 
		return products;
	}
	
	private static Client simpleClient(){
		Client client = new Client();
		client.setId(existingId);
		client.setName(CLIENT_NAME);
		client.setSurname(CLIENT_SURNAME);
		client.setProfit(CLIENT_PROFIT);
		client.setProducts(productsList());
		return client;
	}
	
	private static ClientDto simpleClientDto(){
		ClientDto dto = new ClientDto();
		dto.setId(existingId);
		dto.setName(CLIENT_NAME);
		dto.setSurname(CLIENT_SURNAME);
		dto.setProfit(CLIENT_PROFIT);
		dto.setProductDtos(new LinkedList<ProductDto>());
		return dto;
	}		
}
