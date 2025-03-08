package com.productmanagement.model;
import java.sql.Date;
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getImportedDate() {return importedDate;}

    public void setImportedDate() {this.importedDate = importedDate;}

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", importedDate=" + importedDate +
                '}';
    }
}
