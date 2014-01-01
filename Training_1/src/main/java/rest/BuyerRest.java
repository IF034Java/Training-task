package rest;

import dto.ClientDto;
import facade.BuyerRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/client")
public class BuyerRest {

    private final static Logger LOGGER = LoggerFactory.getLogger(BuyerRest.class);

    @Autowired
    private BuyerRestService buyerRestService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ClientDto getClient(@PathParam("id") String clientId) {
        LOGGER.trace("get client " + buyerRestService.getClient(clientId));
        return buyerRestService.getClient(clientId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClientDto> getAllClients() {
        return buyerRestService.getAllClients();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteClient(@PathParam("id") String clientId) {
        return buyerRestService.deleteClient(clientId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addClient(ClientDto clientDto) {
    	Response.Status status = null;    	
    	if (buyerRestService.addClient(clientDto) == null){
    		status = Response.Status.UNAUTHORIZED;
    	} else {
    		status =Response.Status.ACCEPTED;
    	}
        return Response.status(status).build();    
    }
}