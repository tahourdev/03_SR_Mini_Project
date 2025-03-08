package com.productmanagement.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductDAO {
    private Connection connection;
    private final String url = "jdbc:postgresql://localhost:5432/stock_db";
    private final String user = "postgres";
    private final String password = "@Thavornn122";
    public ProductDAO() {
        connection = null;
    }
    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();
        try{
            connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from products");
            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("unit_price"),
                        resultSet.getInt("quantity"),
                        resultSet.getDate("imported_date").toLocalDate()
                ));
            }
            resultSet.close();
            statement.close();
            connection.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        return products;
    }

    public Product findById(int id){
        String query = "select * from products where id = ?";
        try{
            connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, id);
            ResultSet resultSet = pStatement.executeQuery();
            if(resultSet.next()){
                return new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("unit_price"),
                        resultSet.getInt("quantity"),
                        resultSet.getDate("imported_date").toLocalDate()
                );
            }
            resultSet.close();
            pStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public int getTheLatestIndexID(){
        try{
            connection = DriverManager.getConnection(url, user, password);
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT last_value FROM products_id_seq");
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
            resultSet.close();
            connection.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int addProduct(Product product){
        try{
            connection = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO products (name, unit_price, quantity, imported_date) VALUES (?, ?, ?, ?)";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, product.getName());
            pStatement.setDouble(2, product.getUnitPrice());
            pStatement.setInt(3, product.getQuantity());
            pStatement.setDate(4, product.getImportedDate());
            int isInserted = pStatement.executeUpdate();
            pStatement.close();
            connection.close();
            return isInserted;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
