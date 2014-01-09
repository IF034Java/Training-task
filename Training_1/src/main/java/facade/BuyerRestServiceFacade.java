package facade;

import java.util.List;

import javax.ws.rs.core.Response;

import dto.ClientDto;
import entity.Client;

public interface BuyerRestServiceFacade {

    ClientDto getClient(String clientId);

    List<ClientDto> getAllClients();

    Response deleteClient(String clientId);

    Client addClient(ClientDto clientDto);
    
    List<ClientDto> getProfitableClients();
}
