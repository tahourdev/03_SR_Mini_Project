package com.productmanagement.controller;

import com.productmanagement.model.Product;
import com.productmanagement.model.ProductModel;
import com.productmanagement.model.ProductModelImplementation;
import com.productmanagement.view.ProductView;
import com.productmanagement.view.Utility;

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
    public Product showProductById(int id){
        Product product = model.getProductById(id);
        if(product != null){
            view.displayPage(product);
            return product;
        }else {
            Utility.isNullObject();
            return null;
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

   public void deleteProductById(int id){
        model.deleteProductById(id);
   }

   public void saveProduct(List<Product> list) {
        list.forEach(product -> model.addProduct(product));
   }

   public void updateProduct(List<Product> list) {
        list.forEach(product -> model.updateProduct(product));
   }

   public Integer getPageSize(){
        return model.getPageSize();
   }

   public void setPageSize(Integer pageSize){
        model.setPageSize(pageSize);
   }
}
