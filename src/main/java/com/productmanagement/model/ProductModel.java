package com.productmanagement.model;

import java.util.List;

public interface ProductModel {
    List<Product> getProductsFromDatabase();
    Product getProductById(int id);
    Product getProductByName(String name);
    int getLatestId();
    int addProduct(Product product);
}
