package com.productmanagement.model;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProductDAO {
    private Connection conn;

    public ProductDAO() throws SQLException {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new SQLException("Unable to find db.properties in resources");
            }
            props.load(input);
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new SQLException("Failed to connect to database: " + e.getMessage(), e);
        }
    }

    // Getter for the connection (if needed by other methods)
    public Connection getConnection() {
        return conn;
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
