package com.productmanagement.model;

public class ProductDAOImplementBackup {
    private ProductDAO dao = new ProductDAO();
    public String getBackup(){
        return dao.backUpDatabase();
    }
}
