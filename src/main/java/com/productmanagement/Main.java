package com.productmanagement;

import com.productmanagement.controller.ProductController;
import com.productmanagement.dao.ProductDAO;
import com.productmanagement.view.MenuView;
import com.productmanagement.view.ProductView;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            ProductDAO db = new ProductDAO();
            ProductView view = new ProductView();
            ProductController controller = new ProductController(db, view);
            MenuView menu = new MenuView();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                controller.displayProducts();
                menu.displayMenu();
                String choice = menu.getUserChoice();

                switch (choice) {
                    case "N": // Next Page
                        controller.nextPage();
                        break;
                    case "P": // Previous Page
                        controller.previousPage();
                        break;
                    case "F": // First Page
                        controller.firstPage();
                        break;
                    case "L": // Last Page
                        controller.lastPage();
                        break;
                    case "G": // Go to Page
                        System.out.print("Enter page number: ");
                        int page = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        controller.goToPage(page);
                        break;
                    case "SE": // Set Rows
                        System.out.print("Enter rows per page: ");
                        int rows = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        controller.setRowsPerPage(rows);
                        break;
                    case "E": // Exit
                        System.out.println("Exiting...");
                        return;
                    default:
                        controller.displayProducts(); // Default action: display current page
                        break;
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}


//origin/design-console-ui
