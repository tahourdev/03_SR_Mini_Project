package com.productmanagement.view;

import com.productmanagement.model.Product;

import java.util.List;

public class ProductView {
    public void displayProducts(List<Product> products, int page, int rowsPerPage, int totalProducts) {
        int totalPages = (int) Math.ceil((double) totalProducts / rowsPerPage);
        System.out.println("\n| ID | Name       | Unit Price | Qty | Import Date |");
        System.out.println("|----|------------|------------|-----|-------------|");
        for (Product p : products) {
            System.out.printf("| %-2d | %-10s | %-10.2f | %-3d | %-11s |\n",
                    p.getId(), p.getName(), p.getUnitPrice(), p.getQuantity(), p.getImportedDate());
        }
        System.out.printf("\nPage: %d of %d          Total Records: %d\n", page, totalPages, totalProducts);
    }
}
