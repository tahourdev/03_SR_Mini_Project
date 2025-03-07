package com.productmanagement.model;
import java.time.LocalDate;

public class Product {
    private int id;
    private String name;
    private double unitPrice;
    private int quantity;
    private LocalDate importedDate;

    public Product(int id, String name, double unitPrice, int quantity, LocalDate importedDate) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.importedDate = importedDate;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
    public LocalDate getImportedDate() { return importedDate; }
}
