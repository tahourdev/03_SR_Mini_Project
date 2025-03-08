package com.productmanagement.controller;

import com.productmanagement.model.ProductDAO;
import com.productmanagement.model.Product;
import com.productmanagement.model.ProductModel;
import com.productmanagement.model.ProductModelImplementation;
import com.productmanagement.view.ProductView;
import com.productmanagement.view.Utility;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductController {
    private ProductModel model;
    private ProductView view;
    public ProductController() {
        model = new ProductModelImplementation();
        view = new ProductView();
    }

    public void showAll(ProductController controller) {
        List<Product> list = model.getProductsFromDatabase();
        view.displayAllProducts(list, controller);
    }

    public void updateProduct(ProductController controller) {

    }

    public void showProductById(int id){
        Product product = model.getProductById(id);
        if(product != null){
            view.displayPage(product);
        }else {
            Utility.isNullObject();
        }
    }

    public void showProductByName(String name){
        List<Product> product = model.getProductByName(name);
        if(name != null){
            view.displayListProducts(product);
        } else {
            Utility.isNullObject();
        }
    }

    public int getId(){
        return model.getLatestId() + 1;
    }


}
