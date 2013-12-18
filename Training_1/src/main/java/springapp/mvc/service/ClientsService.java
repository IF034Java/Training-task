package springapp.mvc.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springapp.mvc.dao.ClientsRepository;
import springapp.mvc.entity.Clients;

@Service
@Transactional
public class ClientsService {
	
	@Autowired
	private ClientsRepository clientsRepository;
	
	public void addClient(Clients client){
		clientsRepository.save(client);
	}
	
	public void deleteClient(Integer Id){
		clientsRepository.delete(Id);		
	} 
	
	public Clients getClient(Integer Id){
		return clientsRepository.findOne(Id);		
	}
	
	public List<Clients> getAllClients(){
		return clientsRepository.findAll();		
	}

    public boolean isClientExist(int id){
        boolean isExist = false;
        for (Clients clients : getAllClients()){
           if (clients.getId()==id){
               isExist = true;
           }
        }
        return isExist;
    }
	
}