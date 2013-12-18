package springapp.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springapp.mvc.dao.ProductsRepository;
import springapp.mvc.entity.Products;

@Service
@Transactional
public class ProductsService {
	
	@Autowired
	private ProductsRepository productsRepository;
	
	public void addProduct(Products product){
		productsRepository.save(product);
	}
	
	public void deleteProduct(Integer Id){
		productsRepository.delete(Id);		
	} 
	
	public Products getProduct(Integer Id){
		return productsRepository.findOne(Id);		
	}
	
	public List<Products> getAllProducts(){
		return productsRepository.findAll();		
	}
	
}