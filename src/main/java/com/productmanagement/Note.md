# Update Product Name
```java
public void updateProduct(Product product) throws SQLException {
    String sql = "UPDATE products SET name = ?, unit_price = ?, quantity = ?, imported_date = ? WHERE id = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, product.getName());
    stmt.setDouble(2, product.getUnitPrice());
    stmt.setInt(3, product.getQuantity());
    stmt.setDate(4, Date.valueOf(product.getImportedDate()));
    stmt.setInt(5, product.getId());
    stmt.executeUpdate();
}
```
# Delete Product
```java
public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
```

# Search By Name
```
public List<Product> searchByName(String name) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + name + "%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            products.add(new Product(rs.getInt("id"), rs.getString("name"),
                    rs.getDouble("unit_price"), rs.getInt("quantity"),
                    rs.getDate("imported_date").toLocalDate()));
        }
        return products;
    }
```
