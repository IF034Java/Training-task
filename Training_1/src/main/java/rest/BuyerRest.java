package rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dto.ClientDto;
import entity.Client;
import facade.BuyerRestService;

@Component(value = "customerRESTGateway")
@Path("/Client")
public class BuyerRest {

    @Autowired
    private BuyerRestService buyerRestService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ClientDto getClient(@PathParam("id") String clientId) {
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
    public Response addClient(Client client) {
        return buyerRestService.addClient(client);
    }
}