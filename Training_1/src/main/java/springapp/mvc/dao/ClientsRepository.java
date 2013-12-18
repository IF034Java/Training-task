package springapp.mvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springapp.mvc.entity.Clients;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, Integer>{
	
}