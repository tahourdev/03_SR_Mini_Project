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
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "Manyzuukie12@";
    Utility utility = new Utility();

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
            System.out.println("Executing command: " + String.join(" ", command));
            processBuilder.command(command);
            Process process = processBuilder.start();
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                return "Back-up successful : " + backUpFileName;
            }else {
                return "Back-up failed";
            }
        }catch (IOException | InterruptedException e) {
            return "Error during backup: " + e.getMessage();
        }
    }
    public String restoreDatabase(int backupId)  {
        try{
            File backupDir = new File("src/main/java/database/");
            File[] backups = backupDir.listFiles((dir, name) -> name.endsWith(".sql"));
            if(backups == null || backupId < 1  || backupId > backups.length){
                return "Invalid backup ID";
            }

            java.util.Arrays.sort(backups);
            String backupFileName = backups[backupId - 1].getAbsolutePath();

            ProcessBuilder processBuilder = new ProcessBuilder();
            if (password != null && !password.isEmpty()) {
                processBuilder.environment().put("PGPASSWORD", password);
            }

            String[] command;
            if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
                command = new String[]{
                        "cmd.exe","/c",
                        "C:/Program Files/PostgreSQL/17/bin/psql.exe",
                        "-U", user,
                        "-d", "postgres",
                        "-f", backupFileName
                };
            }else{
                command = new String[]{
                        "psql",
                        "-U", user,
                        "-d", "postgres",
                        "-f", backupFileName
                };
            }
            processBuilder.command(command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                return "Restore successful from: " + backupFileName;
            }else {
                return "Restore failed";
            }
        }catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
