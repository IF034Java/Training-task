package service;

import entity.Product;

import java.util.List;

public interface ProductService {
	Product add(Product product);

    void delete(Integer id);

    Product get(Integer id);

    List<Product> getAll();

    boolean isExist(int id);
}
