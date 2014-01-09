package service.impl;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import repo.ClientRepository;
import service.ClientService;
import config.AppConfigTest;
import entity.Client;
import fixture.ClientFixture;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
@Transactional
public class ClientServiceImplIT {
		
	private static Client simpleClient;
	private static List<Client> inputedClients;
	
	@Autowired
    private ClientService clientService;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@BeforeClass
	public static void initFixtures(){
		simpleClient= new ClientFixture().simpleClient();
		inputedClients = new LinkedList<Client>();
		inputedClients.add(simpleClient);
		
	}
	
	@Test
//	public Client addClient(Client client)
	public void addClientTest(){
		Client actualClient = clientService.addClient(simpleClient);
		Assert.assertEquals(simpleClient.getName(), actualClient.getName());
		Assert.assertEquals(simpleClient.getSurname(), actualClient.getSurname());
		Assert.assertEquals(simpleClient.getProfit(), actualClient.getProfit());		
	}
	
	@Test(expected = InvalidDataAccessApiUsageException.class)
//	public Client addClient(Client client)
	public void addClientExceptionTest(){
		clientService.addClient(null);
	}
	
	@Test
//	public void deleteClient(Integer id)
	public void deleteClientWithIdTest(){		
		clientRepository.save(simpleClient);			
		clientService.deleteClient(findClientId());
		Assert.assertFalse(clientRepository.exists(findClientId()));		
	}
	
	@Test
//	public void deleteClient(Client client)
	public void deleteClientTest(){		
		clientRepository.save(simpleClient);		
		clientService.deleteClient(simpleClient);
		Assert.assertTrue(clientRepository.exists(findClientId()));		
	}
	
	
	@Test
//	public Client getClient(Integer id)
	public void getClientTest(){
		clientRepository.save(simpleClient);		
		Client actualClient = clientService.getClient(findClientId());		
		Assert.assertEquals(simpleClient.getName(), actualClient.getName());
		Assert.assertEquals(simpleClient.getSurname(), actualClient.getSurname());
		Assert.assertEquals(simpleClient.getProfit(), actualClient.getProfit());	
	}
	
	@Test
//	public List<Client> getAllClients()
	public void getAllClientsTest(){
		clientRepository.save(simpleClient);		
		List<Client> actualClients = clientService.getAllClients();		
		Assert.assertTrue(inputedClients.size()==actualClients.size());
		
		for (int i = 0; i<actualClients.size(); i++) {			
			Assert.assertEquals(inputedClients.get(i).getName(), actualClients.get(i).getName());
			Assert.assertEquals(inputedClients.get(i).getSurname(), actualClients.get(i).getSurname());
			Assert.assertEquals(inputedClients.get(i).getProfit(), actualClients.get(i).getProfit());
			
		}
	}
	
	@Test
//	public boolean isClientExist(int id)
	public void isClientExistTest(){
		Assert.assertFalse(clientService.isClientExist(findClientId()));
		clientRepository.save(simpleClient);		
		Assert.assertTrue(clientService.isClientExist(findClientId()));
	}		
	
	@Test
//	public List<Client> getProfitableClients()
	public void getProfitableClientsTest(){
		clientRepository.save(simpleClient);
		List<Client> actualClients = clientService.getProfitableClients();
		Assert.assertTrue(inputedClients.size()==actualClients.size());
		for (Client client : actualClients) {
			Assert.assertTrue(client.getProfit()>100);
		}
	}
	
	private Integer findClientId(){
		Integer searchingId = 0;		
		for (Client client : clientRepository.findAll()) {
			if(client.getName().equals(simpleClient.getName())
					&&client.getSurname().equals(simpleClient.getSurname())
					&&client.getProfit()==simpleClient.getProfit()){
				searchingId = client.getId();
			}
		}		
		return searchingId;
	}
}
