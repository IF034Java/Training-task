package springapp.mvc.REST;

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

import springapp.mvc.entity.Clients;
import springapp.mvc.service.ClientsService;

@Component
@Path("/Clients")
public class ClientsREST {

    @Autowired
    private ClientsService clientsService;

    @GET
    @Path("/getOne/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Clients getClient(@PathParam("id") String clientId) {
        Clients client = clientsService.getClient(Integer.valueOf(clientId));        

        return client;
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.TEXT_HTML)
    public String getAllClients() {
        List<Clients> allClients = clientsService.getAllClients();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><title>Hello</title><body><h3>");
        for (Clients clients : allClients) {
            stringBuffer.append(clients.getId());
            stringBuffer.append(" ");
            stringBuffer.append(clients.getSurname());
            stringBuffer.append(" ");
            stringBuffer.append(clients.getName());
            stringBuffer.append(" ");
            stringBuffer.append(clients.getProfit());
            stringBuffer.append("");
            stringBuffer.append("<p>");
        }
        stringBuffer.append("</body></h3></html>");

        return stringBuffer.toString();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String deleteClient(@PathParam("id") String clientId){
        clientsService.deleteClient(Integer.valueOf(clientId));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><title>Hello</title><body><h3>");
        stringBuffer.append(" ");
        stringBuffer.append("Delete success");
        stringBuffer.append("</body></h3></html>");

        return stringBuffer.toString();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addClient(Clients client){

        clientsService.addClient(client);
        String result = "Client saved : " + client.getSurname();
        return Response.status(201).entity(result).build();
    }

}