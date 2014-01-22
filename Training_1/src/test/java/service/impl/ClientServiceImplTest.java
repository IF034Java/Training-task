package service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import repo.ClientRepository;
import service.ClientService;
import config.AppConfig;
import entity.Client;
import entity.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@Transactional
public class ClientServiceImplTest {
		
	private static Client simpleClient;
	private static List<Client> inputedClients;
	private static double delta;	
	
	private static final Integer ID = 1;
	private static final double CLIENT_PROFIT = 112.12;
	private static final String CLIENT_SURNAME = "Kryzhalko";
	private static final String CLIENT_NAME = "Vitaliy";
	
	@Autowired
    private ClientService clientService;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@PersistenceContext
	 private EntityManager entityManager;		
	
	@BeforeClass
	public static void initFixtures(){
		simpleClient= simpleClient();
		inputedClients = clientsList();		
		delta = 0;				
	}
	
	@Test
//	public Client addClient(Client client)
	public void addClientTest(){
		clientService.add(simpleClient);
		entityManager.flush();
		Integer id = findClientId(simpleClient);
		Client actualClient = clientRepository.findOne(id);
		Assert.assertEquals(CLIENT_NAME, actualClient.getName());
		Assert.assertEquals(CLIENT_SURNAME, actualClient.getSurname());
		Assert.assertEquals(CLIENT_PROFIT, actualClient.getProfit(), delta);		
		int i = 0;
		for (Product product : actualClient.getProducts()) {			
			Assert.assertEquals("Milk" + i, product.getName());
			Assert.assertEquals(8.13*i, product.getPrice(), delta);
			Assert.assertEquals("30.03.14", product.getExpirationDate());
			i++;
		}			
	}	
	
	@Test
//	public void deleteClient(Integer id)
	public void deleteClientWithIdTest(){		
		clientRepository.save(simpleClient);
		entityManager.flush();
		clientService.delete(findClientId(simpleClient));
		Assert.assertFalse(clientRepository.exists(findClientId(simpleClient)));		
	}
	
	@Test
//	public void deleteClient(Client client)
	public void deleteClientTest(){		
		clientRepository.save(simpleClient);
		entityManager.flush();
		Client client= simpleClient;
		client.setId(findClientId(simpleClient));
		clientService.delete(client);						
		Assert.assertFalse(clientRepository.exists(findClientId(client)));		
	}
	
	
	@Test
//	public Client getClient(Integer id)
	public void getClientTest(){
		clientRepository.save(simpleClient);
		entityManager.flush();
		Client actualClient = clientService.get(findClientId(simpleClient));
		Assert.assertEquals(CLIENT_NAME, actualClient.getName());
		Assert.assertEquals(CLIENT_SURNAME, actualClient.getSurname());
		Assert.assertEquals(CLIENT_PROFIT, (double)actualClient.getProfit(), delta);
		List<Product> products = actualClient.getProducts();
		int i = 0;
		for (Product product : products) {			
			Assert.assertEquals("Milk" + i, product.getName());
			Assert.assertEquals(8.13*i, (double)product.getPrice(), delta);
			Assert.assertEquals("30.03.14", product.getExpirationDate());
			i++;
		}		
	}
	
	@Test
//	public List<Client> getAllClients()
	public void getAllClientsTest(){
		clientRepository.save(inputedClients);		
		List<Client> actualClients = clientService.getAll();		
		int i = 0;
		for (Client client : actualClients) {			
			Assert.assertEquals("Vasyl"+ i, client.getName());
			Assert.assertEquals("Vasylchenko" + 2*i, client.getSurname());
			Assert.assertEquals((double)(200+i), client.getProfit(), delta);
			i++;
			int j = 0;
			for (Product product : client.getProducts()) {			
				Assert.assertEquals("Milk" + j, product.getName());
				Assert.assertEquals(8.13*j, product.getPrice(), delta);
				Assert.assertEquals("30.03.14", product.getExpirationDate());
				j++;
			}
			
		}			
	}
	
	@Test
//	public boolean isClientExist(int id)
	public void isClientExistTest(){
		Assert.assertFalse(clientService.isExist(findClientId(simpleClient)));
		clientRepository.save(simpleClient);
		entityManager.flush();
		Assert.assertTrue(clientService.isExist(findClientId(simpleClient)));
	}		
	
	@Test
//	public List<Client> getProfitableClients()
	public void getProfitableClientsTest(){
		List<Client> profitableClients = clientsList();
		clientRepository.save(profitableClients);		
		List<Client> actualClients = clientService.getProfitableClients();
		int i = 0;
		for (Client client : actualClients) {			
			Assert.assertEquals("Vasyl"+ i, client.getName());
			Assert.assertEquals("Vasylchenko" + 2*i, client.getSurname());
			Assert.assertEquals((double)(200+i), client.getProfit(), delta);
			i++;
			int j = 0;
			for (Product product : client.getProducts()) {			
				Assert.assertEquals("Milk" + j, product.getName());
				Assert.assertEquals(8.13*j, product.getPrice(), delta);
				Assert.assertEquals("30.03.14", product.getExpirationDate());
				j++;
			}
			
		}						
	}
		
	private Integer findClientId(Client searchingClient){		
		Integer searchingId = 0;		
		for (Client client : clientRepository.findAll()) {
			if(client.getName().equals(searchingClient.getName())
					&&client.getSurname().equals(searchingClient.getSurname())
					&&client.getProfit()==searchingClient.getProfit()){
				searchingId = client.getId();
			}
		}		
		return searchingId;
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
		client.setId(ID);
		client.setName(CLIENT_NAME);
		client.setSurname(CLIENT_SURNAME);
		client.setProfit(CLIENT_PROFIT);
		client.setProducts(new LinkedList<Product>());
		return client;
	}
	
	private static List<Client> clientsList(){
		List<Client> clients = new LinkedList<Client>();
		for (int i = 0; i < 3; i++) {
			Client client = new Client();
			client.setName("Vasyl"+ i);
			client.setSurname("Vasylchenko" + 2*i);
			client.setProfit((double)(200+i));
			client.setProducts(productsList());
			clients.add(client);
		}		
		return clients;
	}
}
