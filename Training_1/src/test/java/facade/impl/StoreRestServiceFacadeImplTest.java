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
public class StoreRestServiceFacadeImplTest {
	
	private static Client client;
	private static ClientDto clientDto;
	private static Product product;
	private static ProductDto productDto;
	private static List<Client> clients;
	private static List<ClientDto> clientDtos;
	private static List<Product> products;
	private static List<ProductDto> productDtos;
	private boolean productExist;
	
	@Mock
	private ProductService productService;
	
	@Mock
	private ClientService clientService;
	
	@InjectMocks
	private StoreRestServiceFacadeImpl storeRestServiceFacadeImpl;
	
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
		productExist = productService.isProductExist(Integer.valueOf("1"));
	}	
	
	@Test
//	public ProductDto getProduct(String productId)
	public void getExistingProductTest(){
		Mockito.when(productExist).thenReturn(true);
		Mockito.when(productService.getProduct(Integer.valueOf("1"))).thenReturn(product);		
		ProductDto actualProductDto = storeRestServiceFacadeImpl.getProduct("1");
		
		Assert.assertEquals(product.getId(), actualProductDto.getId());
		Assert.assertEquals(product.getName(), actualProductDto.getName());
		Assert.assertEquals(product.getPrice(), actualProductDto.getPrice());
		Assert.assertEquals(product.getExpirationDate(), actualProductDto.getExpirationDate());
	}
	
	@Test
//	public ProductDto getProduct(String productId)
	public void getNonExistingProductTest(){
		Mockito.when(productService.isProductExist(Mockito.anyInt())).thenReturn(false);
		Assert.assertNull(storeRestServiceFacadeImpl.getProduct("1"));
	}
	
	@Test
//	public List<ProductDto> getAllProducts()
	public void getAllProductsTest(){
		Mockito.when(productService.getAllProducts()).thenReturn(products);
		List<ProductDto> actualProductDtos = storeRestServiceFacadeImpl.getAllProducts();
		Assert.assertTrue(products.size()==actualProductDtos.size());		
		
		for(int i = 0; i<actualProductDtos.size(); i++){
			Assert.assertEquals(products.get(i).getId(), actualProductDtos.get(i).getId());
			Assert.assertEquals(products.get(i).getName(), actualProductDtos.get(i).getName());
			Assert.assertEquals(products.get(i).getPrice(), actualProductDtos.get(i).getPrice());
			Assert.assertEquals(products.get(i).getExpirationDate(), actualProductDtos.get(i).getExpirationDate());
		}
	}
	
	@Test
//	public Response deleteProduct(String productId)
	public void deleteExistingProductTest(){
		int status = 200;
		Mockito.when(productExist).thenReturn(true);
		Mockito.when(productService.getProduct(Integer.valueOf(1))).thenReturn(product);
		Assert.assertEquals(status, storeRestServiceFacadeImpl.deleteProduct("1").getStatus());
	}
	
	@Test
//	public Response deleteProduct(String productId)
	public void deleteNonExistingProductTest(){
		Mockito.when(productExist).thenReturn(false);
		Assert.assertNull(storeRestServiceFacadeImpl.deleteProduct("1"));
	}
	
	@Test
//	public Product addProduct(ProductDto productDto)
	public void addProductTest(){
		Mockito.when(productService.addProduct(product)).thenReturn(product);
		Mockito.when(clientService.addClient(client)).thenReturn(client);
		Product actualProduct = storeRestServiceFacadeImpl.addProduct(productDto);
		
		Assert.assertEquals(productDto.getId(), actualProduct.getId());
		Assert.assertEquals(productDto.getName(), actualProduct.getName());
		Assert.assertEquals(productDto.getPrice(), actualProduct.getPrice());
		Assert.assertEquals(productDto.getExpirationDate(), actualProduct.getExpirationDate());
		
	}

}
