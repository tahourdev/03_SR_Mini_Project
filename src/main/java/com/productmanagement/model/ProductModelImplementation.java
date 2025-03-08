package com.productmanagement.model;

import java.util.List;

public class ProductModelImplementation implements ProductModel {
    private ProductDAO dao = new ProductDAO();
    @Override
    public List<Product> getProductsFromDatabase() {
        return dao.getAllProducts();
    }

    @Override
    public Product getProductById(int id) {
        return dao.findById(id);
    }

    @Override
    public int getLatestId() {
        return dao.getTheLatestIndexID();
    }

    @Override
    public void deleteProductById(int id) {
        dao.deleteProduct(id);
    }

}
