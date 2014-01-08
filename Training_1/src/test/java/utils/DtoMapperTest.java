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
import fixture.ProductFixture;

@RunWith(MockitoJUnitRunner.class)
public class DtoMapperTest {
	
	private List<Product> products;
	private List<ProductDto> productDtos;
	private Product product;
	
	@Before
	public void init(){
		product = new ProductFixture().simpleProduct();
		products = new LinkedList<Product>();
		products.add(product);
	}
	
	@Test
	public void mapTest(){
		DtoMapper<Product, ProductDto> dtoMapper = new DtoMapper<Product, ProductDto>(ProductDto.class);
		productDtos = dtoMapper.map(products);
		Assert.assertTrue(products.size()==productDtos.size());
		for(int i = 0; i<products.size(); i++){			
			Assert.assertEquals(products.get(i).getId(), productDtos.get(i).getId());
			Assert.assertEquals(products.get(i).getName(), productDtos.get(i).getName());
			Assert.assertEquals(products.get(i).getPrice(), productDtos.get(i).getPrice());
			Assert.assertEquals(products.get(i).getExpirationDate(), productDtos.get(i).getExpirationDate());
		}
	
	}

}
