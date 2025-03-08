package com.productmanagement.model;

public class ProductDAOImplementRestore {
    private ProductDAO dao = new ProductDAO();
    public String getRestoreDatabase(int backupId) {
        return dao.restoreDatabase(backupId);
    }
}
