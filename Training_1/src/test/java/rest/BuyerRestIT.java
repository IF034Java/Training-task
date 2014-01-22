package rest;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import utils.DbCreator;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import dto.ClientDto;
import dto.ProductDto;

public class BuyerRestIT {	
	
	private static final String BASE_URL = "http://localhost:8080/rest/client";
	private static final double DELTA = 0.01;
	private static final String PRODUCT_EXPIRATION_DATE = "30.03.14";
	private static final double PRODUCT_PRICE = 8.13;
	private static final String PRODUCT_NAME = "Chocolate";
	private static final double CLIENT_PROFIT = 200.0;
	private static final String CLIENT_SURNAME = "Vartus";
	private static final String CLIENT_NAME = "Igor";	
	
	@BeforeClass
	public static void initDb(){
		DbCreator.restoreDb();
	}

	@Test
	// public ClientDto getClient(@PathParam("id") String clientId)
	public void test1GetClientIT() {
		Response response = get(BASE_URL + "/1");
		response.then().assertThat().statusCode(HttpServletResponse.SC_OK);
		response.then().assertThat().contentType(ContentType.JSON);
		ClientDto clientDto = response.as(ClientDto.class);
		assertEquals(CLIENT_NAME+"0", clientDto.getName());
		assertEquals(CLIENT_SURNAME+"0", clientDto.getSurname());
		assertEquals(CLIENT_PROFIT, clientDto.getProfit(), DELTA);
		int j = 0;
		for (ProductDto productDto : clientDto.getProductDtos()) {
			assertEquals(PRODUCT_NAME + j, productDto.getName());
			assertEquals(PRODUCT_PRICE * j, productDto.getPrice(), DELTA);
			assertEquals(PRODUCT_EXPIRATION_DATE, productDto.getExpirationDate());
			j++;
		}
	}

	@Test
	// public List<ClientDto> getAllClients()
	public void test1GetAllClientsIT() {
		Response response = get(BASE_URL);
		response.then().assertThat().statusCode(HttpServletResponse.SC_OK);
		response.then().assertThat().contentType(ContentType.JSON);
		ClientDto[] clientDtos = response.as(ClientDto[].class);
		int i = 0;
		for (ClientDto clientDto : clientDtos) {
			assertEquals(CLIENT_NAME + i, clientDto.getName());
			assertEquals(CLIENT_SURNAME + 2 * i, clientDto.getSurname());
			assertEquals((CLIENT_PROFIT + i), clientDto.getProfit(), DELTA);
			i++;
			int j = 0;
			for (ProductDto productDto : clientDto.getProductDtos()) {
				assertEquals(PRODUCT_NAME + j, productDto.getName());
				assertEquals(PRODUCT_PRICE * j, productDto.getPrice(), DELTA);
				assertEquals(PRODUCT_EXPIRATION_DATE, productDto.getExpirationDate());
				j++;
			}

		}
	}
		
	@SuppressWarnings("unchecked")
	@Test
//  public Response addClient(ClientDto clientDto)  
	public void test1AddDeleteClientIT() {		
		JSONObject clientJson = new JSONObject();
		clientJson.put("name", CLIENT_NAME+"6");
		clientJson.put("surname", CLIENT_SURNAME+"12");
		clientJson.put("profit", CLIENT_PROFIT+6.0);					
		String clientJsonString = clientJson.toJSONString();
		
		given().contentType(ContentType.JSON).
		body(clientJsonString).
	    expect().statusCode(HttpServletResponse.SC_ACCEPTED).
	    when().post(BASE_URL);
		DbCreator.restoreDb();
	}

	@Test
//  public Response deleteClient(@PathParam("id") String clientId)
	public void test4DeleteClientIT() {
		delete(BASE_URL + "/6").then().assertThat().statusCode(HttpServletResponse.SC_OK);
		DbCreator.restoreDb();
	}	
	
	@AfterClass
	public static void deleteDbData(){
		DbCreator.createSchemaDb();
	}
	
}
