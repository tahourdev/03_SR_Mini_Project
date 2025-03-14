package com.productmanagement.model;

import com.productmanagement.view.Utility;

import java.io.File;
import java.io.IOException;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ProductDAO {
    private Connection connection;
    private final String url = "jdbc:postgresql://localhost:5432/stock_db";
    private final String user = "postgres";
    private final String password = "270540";
    Utility utility = new Utility();

    public ProductDAO() {
        connection = null;
    }
    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();
        try{
            connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from products order by id");
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

    public List<Product>  findByName(String name){
        String query = "select * from products where name ilike ?";
        List<Product> products = new ArrayList<>();
        try{
            connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStatement = connection.prepareStatement(query);
            pStatement.setString(1, name.trim() + "%"); // Enable partial matching
            ResultSet resultSet = pStatement.executeQuery();

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
            pStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
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

    public String backUpDatabase() {
        try{
            String backupDir = "src/main/java/database/";
            File file = new File(backupDir).getAbsoluteFile();
            if (!file.exists()) {
                if(file.mkdir()) {
                    throw new IOException("Failed to create backup directory: ");
                }
            }

            String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
            String backUpFileName = "Version" + (utility.countBackups() + 1) + "-product-backup-" + timestamp + ".sql";
            String backUpFilePath = file + "/" + backUpFileName;

            ProcessBuilder processBuilder = new ProcessBuilder();
            if (password != null && !password.isEmpty()) {
                processBuilder.environment().put("PGPASSWORD", password);
            }

            String[] command = new String[]{
                    "C:/Program Files/PostgreSQL/17/bin/pg_dump.exe",
                    "-U", user,
                    "-F", "p",
                    "-f", backUpFilePath,
                    "postgres"
            };
            processBuilder.command(command);
            Process process;
            process = processBuilder.start();
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                return "Back-up successful : " + backUpFileName;
            }else {
                return "Back-up failed";
            }
        }catch (IOException | InterruptedException e) {
            return "Error during backup: " + e.getMessage();
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    public int addProduct(Product product){
        try{
            connection = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO products (name, unit_price, quantity, imported_date) VALUES (?, ?, ?, ?)";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, product.getName());
            pStatement.setDouble(2, product.getUnitPrice());
            pStatement.setInt(3, product.getQuantity());
            pStatement.setDate(4, java.sql.Date.valueOf(product.getImportedDate()));
            int isInserted = pStatement.executeUpdate();
            if(isInserted != 0){
                System.out.println(Utility.CHECK_MARK + Utility.GREEN +" Product with ID: " + product.getId() + " was added to the database successfully!!!" + Utility.RESET_TEXT_COLOUR);
            }
            pStatement.close();
            connection.close();
            return isInserted;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void deleteProduct(int id){

        String sql = "DELETE FROM products WHERE id = ?";
        try {
            connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateProduct(Product product){
        try{
            connection = DriverManager.getConnection(url, user, password);
            String sql = "UPDATE products SET name = ?, unit_price = ?, quantity = ?, imported_date = ? WHERE id = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, product.getName());
            pStatement.setDouble(2, product.getUnitPrice());
            pStatement.setInt(3, product.getQuantity());
            pStatement.setDate(4, java.sql.Date.valueOf(product.getImportedDate()));
            pStatement.setInt(5, product.getId());
            int isInserted = pStatement.executeUpdate();
            if(isInserted != 0){
                System.out.println(Utility.CHECK_MARK + Utility.GREEN +" Product with ID: " + product.getId() + " was updated to the database successfully!!!" + Utility.RESET_TEXT_COLOUR);
            }
            pStatement.close();
            connection.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer getPageSize(){
        try{
            connection = DriverManager.getConnection(url, user, password);
            ResultSet rs = connection.createStatement().executeQuery("SELECT value FROM config WHERE id = 1");
            if(rs.next()){
                return rs.getInt(1);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void setPageSize(int pageSize){
        try{
            connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStatement = connection.prepareStatement("UPDATE config SET value = ? WHERE id = 1");
            pStatement.setInt(1, pageSize);
            pStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
