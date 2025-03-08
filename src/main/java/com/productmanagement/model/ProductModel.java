package com.productmanagement.model;

import java.util.List;

public interface ProductModel {
    List<Product> getProductsFromDatabase();
    Product getProductById(int id);
    List<Product>  getProductByName(String name);
    int getLatestId();
    void deleteProductById(int id);
    int addProduct(Product product);
}
