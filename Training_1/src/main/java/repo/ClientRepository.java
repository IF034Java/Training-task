package repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{
	
}