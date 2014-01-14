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

import repo.ProductRepository;
import service.ProductService;
import config.AppConfigTest;
import entity.Client;
import entity.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
@Transactional
public class ProductServiceImplIT {
	
	private static Product simpleProduct;
	private static List<Product> inputedProducts;
	private static double delta;	
	
	private static final String PRODUCT_NAME = "Bread";
	private static final double PRODUCT_PRICE = 4.25;
	private static final String PRODUCT_EXPARATION_DATE = "30.03.14";
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	 @PersistenceContext
	 private EntityManager entityManager;
	
	@BeforeClass
	public static void initFixtures(){
		simpleProduct = simpleProduct();
		inputedProducts = productsList();
		delta = 0;
	}
	
	@Test
//	public Product addProduct(Product product)
	public void addProductTest(){
		productService.add(simpleProduct);
		entityManager.flush();
		Integer id = findProductId(simpleProduct);		
		Product actualProduct = productRepository.findOne(id);
		Assert.assertEquals(PRODUCT_NAME, actualProduct.getName());
		Assert.assertEquals(PRODUCT_PRICE, actualProduct.getPrice(), delta);
		Assert.assertEquals(PRODUCT_EXPARATION_DATE, actualProduct.getExpirationDate());
		int i = 0;
		for (Client client : actualProduct.getClients()) {			
			Assert.assertEquals("Vasyl"+ i, client.getName());
			Assert.assertEquals("Vasylchenko" + 2*i, client.getSurname());
			Assert.assertEquals((double)(200+i), client.getProfit(), delta);
			i++;
		}			
	}
	
	@Test
//	public void deleteProduct(Integer id)
	public void deleteProductWithIdTest(){		
		productRepository.save(simpleProduct);
		productService.delete(findProductId(simpleProduct));
		entityManager.flush();
		Assert.assertFalse(productRepository.exists(findProductId(simpleProduct)));
	}
	
	@Test
//	public Product getProduct(Integer id)
	public void getProductTest(){
		productRepository.save(simpleProduct);
		entityManager.flush();
		Product actualProduct = productService.get(findProductId(simpleProduct));
		Assert.assertEquals(PRODUCT_NAME, actualProduct.getName());
		Assert.assertEquals(PRODUCT_PRICE, actualProduct.getPrice(), delta);
		Assert.assertEquals(PRODUCT_EXPARATION_DATE, actualProduct.getExpirationDate());
		int i = 0;
		for (Client client : actualProduct.getClients()) {			
			Assert.assertEquals("Vasyl"+ i, client.getName());
			Assert.assertEquals("Vasylchenko" + 2*i, client.getSurname());
			Assert.assertEquals((double)(200+i), client.getProfit(), delta);
			i++;
		}
	}
	
	@Test
//	public List<Product> getAllProducts()
	public void getAllProductsTest(){		
		productRepository.save(inputedProducts);
		entityManager.flush();
		List<Product> actualProducts = productService.getAll();
		Assert.assertTrue(inputedProducts.size()==actualProducts.size());		
		int i = 0;
		for (Product product : actualProducts) {			
			Assert.assertEquals("Milk" + i, product.getName());
			Assert.assertEquals(8.13*i, product.getPrice(), delta);
			Assert.assertEquals("30.03.14", product.getExpirationDate());
			i++;			
			int j = 0;
			for (Client client : product.getClients()) {			
				Assert.assertEquals("Vasyl"+ j, client.getName());
				Assert.assertEquals("Vasylchenko" + 2*j, client.getSurname());
				Assert.assertEquals((double)(200+j), client.getProfit(), delta);				
				j++;
			}
			
		}			
	}
	
	@Test
//	public boolean isProductExist(int id)
	public void isProductExistTest(){
		Assert.assertFalse(productService.isExist(findProductId(simpleProduct)));
		productRepository.save(simpleProduct);
		entityManager.flush();
		Assert.assertTrue(productService.isExist(findProductId(simpleProduct)));		
	}
		
	private Integer findProductId(Product searchingProduct){
		Integer searchingId = 0;		
		for (Product product : productRepository.findAll()) {
			if(product.getName().equals(searchingProduct.getName())
					&&product.getExpirationDate().equals(searchingProduct.getExpirationDate())
					&&product.getPrice()==searchingProduct.getPrice()){
				searchingId = product.getId();
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
			product.setClients(clientsList());			
			products.add(product);			
		} 
		return products;
	}		
	
	private static Product simpleProduct(){
		Product product = new Product();		
		product.setName(PRODUCT_NAME);
		product.setPrice(PRODUCT_PRICE);
		product.setExpirationDate(PRODUCT_EXPARATION_DATE);
		product.setClients(clientsList());		
		
		return product;
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
	
}
