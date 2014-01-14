package utils;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import dto.ProductDto;
import entity.Product;

@RunWith(MockitoJUnitRunner.class)
public class DtoMapperTest {
	
	private List<Product> products;	
	private double delta;				
	
	
	@Before
	public void init(){		
		products = productsList();
		delta = 0;
	}
	
	@Test
	public void mapTest(){
		DtoMapper<Product, ProductDto> dtoMapper = new DtoMapper<Product, ProductDto>(ProductDto.class);
		List<ProductDto> productDtos = dtoMapper.map(products);		
		int i = 0;
		for (ProductDto productDto : productDtos) {			
			Assert.assertEquals(2 + i, productDto.getId(), delta);
			Assert.assertEquals("Milk" + i, productDto.getName());
			Assert.assertEquals(8.13*i, productDto.getPrice(), delta);
			Assert.assertEquals("30.03.14", productDto.getExpirationDate());
			i++;			
		}			
	}
	
	private static List<Product> productsList(){
		List<Product> products = new LinkedList<Product>();
		for (int i = 0; i < 3; i++) {
			Product product = new Product();
			product.setId(2+i);
			product.setName("Milk" + i);
			product.setPrice(8.13*i);
			product.setExpirationDate("30.03.14");			
			products.add(product);			
		} 
		return products;
	}			
}
