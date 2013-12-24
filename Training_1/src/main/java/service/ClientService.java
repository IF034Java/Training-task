package service;

import entity.Client;

import java.util.List;

public interface ClientService {
    void addClient(Client client);

    void deleteClient(Integer id);

    void deleteClient(Client client);

    Client getClient(Integer id);

    List<Client> getAllClients();

    boolean isClientExist(int id);
    
    List<Client> getProfitableClients();
}
