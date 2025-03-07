package com.productmanagement.controller;

import com.productmanagement.model.DatabaseManager;
import com.productmanagement.model.Product;
import com.productmanagement.view.ProductView;

import java.sql.SQLException;
import java.util.List;

public class ProductController {
    private DatabaseManager db;
    private ProductView view;
    private int rowsPerPage = 5; // Default rows per page
    private int currentPage = 1;

    public ProductController(DatabaseManager db, ProductView view) {
        this.db = db;
        this.view = view;
    }

    public void displayProducts() throws SQLException {
        List<Product> products = db.getProducts(currentPage, rowsPerPage);
        int totalProducts = db.getTotalProducts();
        view.displayProducts(products, currentPage, rowsPerPage, totalProducts);
    }

    // Pagination controls
    public void nextPage() throws SQLException {
        int totalProducts = db.getTotalProducts();
        int totalPages = (int) Math.ceil((double) totalProducts / rowsPerPage);
        if (currentPage < totalPages) {
            currentPage++;
            displayProducts();
        } else {
            System.out.println("Already on the last page.");
        }
    }

    public void previousPage() throws SQLException {
        if (currentPage > 1) {
            currentPage--;
            displayProducts();
        } else {
            System.out.println("Already on the first page.");
        }
    }

    public void firstPage() throws SQLException {
        currentPage = 1;
        displayProducts();
    }

    public void lastPage() throws SQLException {
        int totalProducts = db.getTotalProducts();
        int totalPages = (int) Math.ceil((double) totalProducts / rowsPerPage);
        currentPage = totalPages;
        displayProducts();
    }

    public void goToPage(int page) throws SQLException {
        int totalProducts = db.getTotalProducts();
        int totalPages = (int) Math.ceil((double) totalProducts / rowsPerPage);
        if (page >= 1 && page <= totalPages) {
            currentPage = page;
            displayProducts();
        } else {
            System.out.println("Invalid page number. Must be between 1 and " + totalPages);
        }
    }

    public void setRowsPerPage(int rows) throws SQLException {
        if (rows > 0) {
            rowsPerPage = rows;
            currentPage = 1; // Reset to first page
            displayProducts();
        } else {
            System.out.println("Rows per page must be positive.");
        }
    }
}
