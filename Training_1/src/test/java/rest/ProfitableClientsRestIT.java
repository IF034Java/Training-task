package rest;

import static com.jayway.restassured.RestAssured.get;
import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletResponse;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import utils.DbCreator;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import dto.ClientDto;
import dto.ProductDto;

public class ProfitableClientsRestIT {			
	
	private static final String URL = "http://localhost:8080/rest/profitableClients";
	private static final double DELTA = 0.0; 		
	
	@BeforeClass
	public static void initDb(){
		DbCreator.restoreDb();
	}
	
	@Test
//	public List<ClientDto> getProfitClients()
	public void GetProfitClientsIT(){		
		Response response = get(URL);
		response.then().assertThat().statusCode(HttpServletResponse.SC_OK);							  		
		response.then().assertThat().contentType(ContentType.JSON);
		
		ClientDto[] clientDtos = response.as(ClientDto[].class);
		int i = 0;		
		for (ClientDto clientDto : clientDtos) {
			assertEquals("Igor"+ i, clientDto.getName());
			assertEquals("Vartus" + 2*i, clientDto.getSurname());
			assertEquals((200+i), clientDto.getProfit(), DELTA);
			i++;
			int j = 0;
			for (ProductDto productDto : clientDto.getProductDtos()) {
				assertEquals("Chocolate" + j, productDto.getName());
				assertEquals(8.13*j, productDto.getPrice(), DELTA);
				assertEquals("30.03.14", productDto.getExpirationDate());
				j++;
			}
			
		}		  	 		  		  			
		
	}
	
	@AfterClass
	public static void deleteDbData(){
		DbCreator.createSchemaDb();
	}
	
}

