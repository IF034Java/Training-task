package rest;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

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
public class BuyerRestIT {	
	
	private static final String BASE_URL = "http://localhost:8080/rest/client";
	private static final double DELTA = 0.0;
	private static final String PRODUCT_EXPIRATION_DATE = "30.03.14";
	private static final double PRODUCT_PRICE = 8.13;
	private static final String PRODUCT_NAME = "Chocolate";
	private static final double CLIENT_PROFIT = 200.0;
	private static final String CLIENT_SURNAME = "Vartus";
	private static final String CLIENT_NAME = "Igor";
	public static final Logger logger = LoggerFactory.getLogger(BuyerRestIT.class);

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
		clientJson.put("name", CLIENT_NAME+"5");
		clientJson.put("surname", CLIENT_SURNAME+"10");
		clientJson.put("profit", CLIENT_PROFIT+5.0);					
		String clientJsonString = clientJson.toJSONString();
		
		given().contentType(ContentType.JSON).
		body(clientJsonString).
	    expect().statusCode(HttpServletResponse.SC_ACCEPTED).
	    when().post(BASE_URL);							
	}

	@Test
//  public Response deleteClient(@PathParam("id") String clientId)
	public void test4DeleteClientIT() {
		Response response = get(BASE_URL);
		int searchingId = 0;
		ClientDto[] clientDtos = response.as(ClientDto[].class);
		for (ClientDto clientDto : clientDtos) {			
			if(clientDto.getName().equals(CLIENT_NAME+"5")){
				searchingId = clientDto.getId();
			}
		}
		delete(BASE_URL + "/"+String.valueOf(searchingId)).then().assertThat().statusCode(HttpServletResponse.SC_OK);				
	}
	
}
