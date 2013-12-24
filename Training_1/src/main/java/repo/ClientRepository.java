package repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{
	
	@Query("select e from Client e where e.profit>100 ")
	List<Client> getProfitableClients(); 
	
}