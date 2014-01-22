package rest;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import dto.ClientDto;
import dto.ProductDto;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StoreRestIT {
	
	private static final String BASE_URL = "http://localhost:8080/rest/product";
	private static final double DELTA = 0.0;
	private static final String PRODUCT_EXPIRATION_DATE = "30.03.14";
	private static final double PRODUCT_PRICE = 8.13;
	private static final String PRODUCT_NAME = "Chocolate";
	private static final double CLIENT_PROFIT = 200.0;
	private static final String CLIENT_SURNAME = "Vartus";
	private static final String CLIENT_NAME = "Igor";
	public static final Logger logger = LoggerFactory.getLogger(StoreRestIT.class);

	@Test
	// public ProductDto getProduct(@PathParam("id") String productId)
	public void test1GetProductIT() {
		Response response = get(BASE_URL + "/1");
		response.then().assertThat().statusCode(HttpServletResponse.SC_OK);
		response.then().assertThat().contentType(ContentType.JSON);
		ProductDto productDto = response.as(ProductDto.class);
		assertEquals(PRODUCT_NAME + "0", productDto.getName());
		assertEquals(PRODUCT_PRICE * 0, productDto.getPrice(), DELTA);
		assertEquals(PRODUCT_EXPIRATION_DATE, productDto.getExpirationDate());
		int j = 0;
		for (ClientDto clientDto : productDto.getClientDtos()) {
			assertEquals(CLIENT_NAME + j, clientDto.getName());
			assertEquals(CLIENT_SURNAME + 2 * j, clientDto.getSurname());
			assertEquals((CLIENT_PROFIT + j), clientDto.getProfit(), DELTA);
			j++;
		}
	}

	@Test
	// public List<ProductDto> getAllProducts()
	public void test1GetAllProductsIT() {
		Response response = get(BASE_URL);
		response.then().assertThat().statusCode(HttpServletResponse.SC_OK);
		response.then().assertThat().contentType(ContentType.JSON);
		ProductDto[] productDtos = response.as(ProductDto[].class);
		int j = 0;
		for (ProductDto productDto : productDtos) {
			assertEquals(PRODUCT_NAME + j, productDto.getName());
			assertEquals(PRODUCT_PRICE * j, productDto.getPrice(), DELTA);
			assertEquals(PRODUCT_EXPIRATION_DATE,
					productDto.getExpirationDate());
			j++;
			int i = 0;
			for (ClientDto clientDto : productDto.getClientDtos()) {
				assertEquals(CLIENT_NAME + i, clientDto.getName());
				assertEquals(CLIENT_SURNAME + 2 * i, clientDto.getSurname());
				assertEquals((CLIENT_PROFIT + i), clientDto.getProfit(), DELTA);
				i++;
			}

		}
	}

	@SuppressWarnings("unchecked")
	@Test
	// public Response addProduct(ProductDto productDto)
	public void test3AddProductIT() {		
		JSONObject productJson = new JSONObject();
		productJson.put("name", PRODUCT_NAME + "5");
		productJson.put("price", PRODUCT_PRICE * 5.0);
		productJson.put("expirationDate", PRODUCT_EXPIRATION_DATE);
		String productJsonString = productJson.toJSONString();

		given().contentType(ContentType.JSON).body(productJsonString).expect()
				.statusCode(HttpServletResponse.SC_ACCEPTED).when()
				.post(BASE_URL);		
	}

	@Test
	// public Response deleteProduct(@PathParam("id") String productId)
	public void test4DeleteProductIT() {
		Response response = get(BASE_URL);
		int searchingId = 0;
		ProductDto[] productDtos = response.as(ProductDto[].class);
		for (ProductDto productDto : productDtos) {
			if(productDto.getName().equals(PRODUCT_NAME+"5")){
				searchingId = productDto.getId();
			}			
		}
		delete(BASE_URL + "/" + String.valueOf(searchingId)).then().assertThat().statusCode(HttpServletResponse.SC_OK);													
	}
		
}
