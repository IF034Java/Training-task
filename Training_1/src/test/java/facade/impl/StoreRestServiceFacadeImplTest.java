package facade.impl;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Assert;

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
public class StoreRestServiceFacadeImplTest {
		
	private static Product product;
	private static ProductDto productDto;
	private static List<Client> clients;	
	private static List<Product> products;	
		
	private static final String PRODUCT_NAME = "Bread";
	private static final double PRODUCT_PRICE = 4.25;
	private static final String PRODUCT_EXPARATION_DATE = "30.03.14";
	private static final double DELTA = 0.01;
	private static Integer existingId = 1;
	private static Integer notExistingId = -1;
	
	@Mock
	private ProductService productService;
	
	@Mock
	private ClientService clientService;
	
	@InjectMocks
	private StoreRestServiceFacadeImpl storeRestServiceFacadeImpl;
	
	@BeforeClass
	public static void initInputs(){									
		clients = clientsList();						
		product = simpleProduct();
		productDto = simpleProductDto();		
		products = productsList();		
		
	}
	
	@Before
	public void initMocks(){
		MockitoAnnotations.initMocks(this);
		mockProductService();		
	}
	
	private void mockProductService(){
		Mockito.when(productService.isExist(existingId)).thenReturn(true);
		Mockito.when(productService.isExist(notExistingId)).thenReturn(false);
		Mockito.when(productService.get(existingId)).thenReturn(product);
		Mockito.when(productService.getAll()).thenReturn(products);
		Mockito.when(productService.add(Mockito.any(Product.class))).thenAnswer(new Answer<Product>() {
            @Override
            public Product answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Product) args[0];
            }
        });
	}
	
	@Test
//	public ProductDto getProduct(String productId)
	public void getExistingProductTest(){			
		ProductDto actualProductDto = storeRestServiceFacadeImpl.getProduct("1");
		Assert.assertEquals(PRODUCT_NAME, actualProductDto.getName());
		Assert.assertEquals(PRODUCT_PRICE, actualProductDto.getPrice(), DELTA);
		Assert.assertEquals(PRODUCT_EXPARATION_DATE, actualProductDto.getExpirationDate());
		List<ClientDto> clientDtos = actualProductDto.getClientDtos();
		int i = 0;
		for (ClientDto clientDto : clientDtos) {			
			Assert.assertEquals("Vasyl"+ i, clientDto.getName());
			Assert.assertEquals("Vasylchenko" + 2*i, clientDto.getSurname());
			Assert.assertEquals((double)(200+i), clientDto.getProfit(), DELTA);			
			i++;
		}							
	}
	
	@Test
//	public ProductDto getProduct(String productId)
	public void getNonExistingProductTest(){		
		Assert.assertNull(storeRestServiceFacadeImpl.getProduct("-1"));
	}
	
	@Test
//	public List<ProductDto> getAllProducts()
	public void getAllProductsTest(){		
		List<ProductDto> actualProductDtos = storeRestServiceFacadeImpl.getAllProducts();				
		int i=0;
		for (ProductDto productDto : actualProductDtos) {
			Assert.assertEquals("Milk" + i, productDto.getName());
			Assert.assertEquals(8.13*i, productDto.getPrice(), DELTA);
			Assert.assertEquals("30.03.14", productDto.getExpirationDate());
			i++;
			int j=0;
			for (ClientDto clientDto : productDto.getClientDtos()) {			
				Assert.assertEquals("Vasyl"+ j, clientDto.getName());
				Assert.assertEquals("Vasylchenko" + 2*j, clientDto.getName());
				Assert.assertEquals((double)(200+j), clientDto.getName());
				j++;
			}
		}				
	}
	
	@Test
//	public Response deleteProduct(String productId)
	public void deleteExistingProductTest(){
		Response response = storeRestServiceFacadeImpl.deleteProduct("1");				
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
//	public Response deleteProduct(String productId)
	public void deleteNonExistingProductTest(){		
		Assert.assertNull(storeRestServiceFacadeImpl.deleteProduct("-1"));
	}
	
	@Test
//	public Product addProduct(ProductDto productDto)
	public void addProductTest(){					
		Product actualProduct = storeRestServiceFacadeImpl.addProduct(productDto);
		Assert.assertEquals(PRODUCT_NAME, actualProduct.getName());
		Assert.assertEquals(PRODUCT_PRICE, actualProduct.getPrice(), DELTA);
		Assert.assertEquals(PRODUCT_EXPARATION_DATE, actualProduct.getExpirationDate());
		int i = 0;
		for (Client client : clients) {			
			Assert.assertEquals("Vasyl"+ i, client.getName());
			Assert.assertEquals("Vasylchenko" + 2*i, client.getSurname());
			Assert.assertEquals((double)(200+i), client.getProfit(), DELTA);			
			i++;
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
	
	private static Product simpleProduct(){
		Product product = new Product();
		product.setId(existingId);
		product.setName(PRODUCT_NAME);
		product.setPrice(PRODUCT_PRICE);
		product.setExpirationDate(PRODUCT_EXPARATION_DATE);
		product.setClients(clientsList());		
		
		return product;
	}
	
	private static ProductDto simpleProductDto(){
		ProductDto productDto = new ProductDto();
		productDto.setId(existingId);
		productDto.setName(PRODUCT_NAME);
		productDto.setPrice(PRODUCT_PRICE);
		productDto.setExpirationDate(PRODUCT_EXPARATION_DATE);
		productDto.setClientDtos(new LinkedList<ClientDto>());		
		
		return productDto;
	}		

}
