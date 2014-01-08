package rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import dto.ClientDto;
import facade.BuyerRestServiceFacade;

@Path("/profitableClients")
public class ProfitableClientsRest {
	
	@Autowired 
	private BuyerRestServiceFacade buyerRestServiceFacade;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClientDto> getProfitClients() {
        return buyerRestServiceFacade.getProfitableClients();
    }

}
