package com.productmanagement.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection conn;

    public DatabaseManager() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/stock_db";
        String user = "enghour";
        String password = "Tahour@202023";
        conn = DriverManager.getConnection(url, user, password);
    }

    // Fetch products with pagination
    public List<Product> getProducts(int page, int rowsPerPage) throws SQLException {
        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * rowsPerPage;
        String sql = "SELECT * FROM products ORDER BY id LIMIT ? OFFSET ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, rowsPerPage);
        stmt.setInt(2, offset);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            products.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("unit_price"),
                    rs.getInt("quantity"),
                    rs.getDate("imported_date").toLocalDate()
            ));
        }
        return products;
    }

    // Get total number of products for pagination
    public int getTotalProducts() throws SQLException {
        String sql = "SELECT COUNT(*) FROM products";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        rs.next();
        return rs.getInt(1);
    }
}
