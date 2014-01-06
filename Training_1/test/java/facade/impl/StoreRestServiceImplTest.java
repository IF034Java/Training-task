package facade.impl;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import dto.ClientDto;
import dto.ProductDto;
import entity.Client;
import entity.Product;
import fixture.Entity;
import service.ClientService;
import service.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class StoreRestServiceImplTest {
	
	private Client client;
	private ClientDto clientDto;
	private Product product;
	private ProductDto productDto;
	private List<Client> clients;
	private List<ClientDto> clientDtos;
	private List<Product> products;
	private List<ProductDto> productDtos;
	
	@Mock
	private ProductService productService;
	
	@InjectMocks
	private StoreRestServiceImpl storeRestServiceImpl;
	
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
//	public List<ClientDto> clientDtoMapper(List<Client> clients)
	public void productDtoMapper(){
		Assert.assertTrue(clientDtos.size()==storeRestServiceImpl.clientDtoMapper(clients).size());		
		Assert.assertTrue(clientDtos.containsAll(storeRestServiceImpl.clientDtoMapper(clients)));
		
	}
	
	@Test
//	public ProductDto getProduct(String productId)
	public void getExistingProductTest(){
		Mockito.when(productService.isProductExist(Integer.valueOf("1"))).thenReturn(true);
		Mockito.when(productService.getProduct(Integer.valueOf("1"))).thenReturn(product);
		Assert.assertEquals(productDto, storeRestServiceImpl.getProduct("1"));
	}
	
	@Test
//	public ProductDto getProduct(String productId)
	public void getNonExistingProductTest(){
		Mockito.when(productService.isProductExist(Mockito.anyInt())).thenReturn(false);
		Assert.assertNull(storeRestServiceImpl.getProduct("1"));
	}
	
	@Test
//	public List<ProductDto> getAllProducts()
	public void getAllProductsTest(){
		Mockito.when(productService.getAllProducts()).thenReturn(products);
		Assert.assertTrue(productDtos.size()==storeRestServiceImpl.getAllProducts().size());
		Assert.assertTrue(productDtos.containsAll(storeRestServiceImpl.getAllProducts()));
	}
	
	@Test
//	public Response deleteProduct(String productId)
	public void deleteExistingProductTest(){
		int status = 200;
		Mockito.when(productService.isProductExist(Integer.valueOf(1))).thenReturn(true);
		Mockito.when(productService.getProduct(Integer.valueOf(1))).thenReturn(product);
		Assert.assertEquals(status, storeRestServiceImpl.deleteProduct("1").getStatus());
	}
	
	@Test
//	public Response deleteProduct(String productId)
	public void deleteNonExistingProductTest(){
		Mockito.when(productService.isProductExist(Integer.valueOf(1))).thenReturn(false);
		Assert.assertNull(storeRestServiceImpl.deleteProduct("1"));
	}
	
	@Test
//	public Product addProduct(ProductDto productDto)
	public void addProductTest(){
		Mockito.when(productService.addProduct(product)).thenReturn(product);
		Assert.assertEquals(product, storeRestServiceImpl.addProduct(productDto));
	}

}
