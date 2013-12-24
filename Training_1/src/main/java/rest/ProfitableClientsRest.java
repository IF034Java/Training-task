package rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dto.ClientDto;
import facade.BuyerRestService;

@Component(value = "profitableRESTGateway")
@Path("/ProfitableClients")
public class ProfitableClientsRest {
	
	@Autowired 
	private BuyerRestService buyerRestService;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClientDto> getProfitClients() {
        return buyerRestService.getProfitableClients();
    }

}
