package com.productmanagement.controller;

import com.productmanagement.model.*;
import com.productmanagement.view.ProductView;
import com.productmanagement.view.Utility;

import java.util.List;
import java.io.File;

public class ProductController {
    private ProductModel model;
    private ProductView view;
    private ProductDAOImplementBackup backup;
    private ProductDAOImplementRestore restore;
    public ProductController() {
        model = new ProductModelImplementation();
        view = new ProductView();
        backup = new ProductDAOImplementBackup();
        restore = new ProductDAOImplementRestore();
    }
    public String backUpFile() {
        String backupsResult = backup.getBackup();
        return backupsResult;
    }
    public  String restoreDatabase(int backupId) {
        String restoreResult = restore.getRestoreDatabase(backupId);
        return restoreResult;
    }
    public String[] listBackups() {
        File backupDir = new File("src/main/java/database/");
        File[] backups = backupDir.listFiles((dir, name) -> name.endsWith(".sql"));
        if (backups == null || backups.length == 0) {
            return new String[]{"No backups available."};
        }
        java.util.Arrays.sort(backups);
        String[] backupList = new String[backups.length];
        for (int i = 0; i < backups.length; i++) {
            backupList[i] = (i + 1) + "\t" + backups[i].getName();
        }
        return backupList;
    }

    public void showAll(ProductController controller) {
        List<Product> list = model.getProductsFromDatabase();
        view.displayAllProducts(list, controller);
    }

    public void showProductById(int id){
        Product product = model.getProductById(id);
        if(product != null){
            view.displayPage(product);
        }else {
            Utility.isNullObject();
        }
    }

    public int getId(){
        return model.getLatestId() + 1;
    }

}
