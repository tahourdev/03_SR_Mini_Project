package com.productmanagement.model;

import java.util.List;

public interface ProductModel {
    List<Product> getProductsFromDatabase();
    Product getProductById(int id);
    int getLatestId();
    int addProduct(Product product);
}
