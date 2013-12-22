package service;

import entity.Product;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);

    void deleteProduct(Integer id);

    Product getProduct(Integer id);

    List<Product> getAllProducts();

    boolean isProductExist(int id);
}
