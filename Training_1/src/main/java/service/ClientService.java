package service;

import entity.Client;

import java.util.List;

public interface ClientService {
    Client add(Client client);

    void delete(Integer id);

    void delete(Client client);

    Client get(Integer id);

    List<Client> getAll();

    boolean isExist(int id);
    
    List<Client> getProfitableClients();
}
