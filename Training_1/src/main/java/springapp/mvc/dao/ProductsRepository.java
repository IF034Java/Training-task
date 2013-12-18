package springapp.mvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springapp.mvc.entity.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer>{
	
}