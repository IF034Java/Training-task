package service.impl;

import java.util.LinkedList;
import java.util.List;

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
import entity.Product;
import fixture.ProductFixture;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
@Transactional
public class ProductServiceImplIT {
	
	private static Product simpleProduct;
	private static List<Product> inputedProducts;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@BeforeClass
	public static void initFixtures(){
		simpleProduct = new ProductFixture().simpleProduct();
		inputedProducts = new LinkedList<Product>();
		inputedProducts.add(simpleProduct);
	}
	
	@Test
//	public Product addProduct(Product product)
	public void addProductTest(){
		Product actualProduct = productService.addProduct(simpleProduct);
		Assert.assertEquals(simpleProduct.getName(), actualProduct.getName());
		Assert.assertEquals(simpleProduct.getPrice(), actualProduct.getPrice());
		Assert.assertEquals(simpleProduct.getExpirationDate(), actualProduct.getExpirationDate());
	}
	
	@Test
//	public void deleteProduct(Integer id)
	public void deleteProductWithIdTest(){		
		productRepository.save(simpleProduct);
		productService.deleteProduct(findProductId());
		Assert.assertFalse(productRepository.exists(findProductId()));
	}
	
	@Test
//	public Product getProduct(Integer id)
	public void getProductTest(){
		productRepository.save(simpleProduct);
		Product actualProduct = productService.getProduct(findProductId());
		Assert.assertEquals(simpleProduct.getName(), actualProduct.getName());
		Assert.assertEquals(simpleProduct.getExpirationDate(), actualProduct.getExpirationDate());
		Assert.assertEquals(simpleProduct.getPrice(), actualProduct.getPrice());
	}
	
	@Test
//	public List<Product> getAllProducts()
	public void getAllProductsTest(){		
		productRepository.save(simpleProduct);
		List<Product> actualProducts = productService.getAllProducts();
		Assert.assertTrue(inputedProducts.size()==actualProducts.size());
		
		for (int i = 0; i<actualProducts.size(); i++) {
			Assert.assertEquals(inputedProducts.get(i).getName(), actualProducts.get(i).getName());
			Assert.assertEquals(inputedProducts.get(i).getPrice(), actualProducts.get(i).getPrice());
			Assert.assertEquals(inputedProducts.get(i).getExpirationDate(), actualProducts.get(i).getExpirationDate());
		}
	}
	
	@Test
//	public boolean isProductExist(int id)
	public void isProductExistTest(){
		Assert.assertFalse(productService.isProductExist(findProductId()));
		productRepository.save(simpleProduct);
		Assert.assertTrue(productService.isProductExist(findProductId()));		
	}
	
	private Integer findProductId(){
		Integer searchingId = 0;		
		for (Product product : productRepository.findAll()) {
			if(product.getName().equals(simpleProduct.getName())
					&&product.getExpirationDate().equals(simpleProduct.getExpirationDate())
					&&product.getPrice()==simpleProduct.getPrice()){
				searchingId = product.getId();
			}
		}		
		return searchingId;
	}
	
}
