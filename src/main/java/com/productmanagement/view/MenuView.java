package com.productmanagement.view;

import java.util.Scanner;

public class MenuView {
    private Scanner scanner;
    public MenuView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("\n=== Stock Management Menu ===");
        System.out.println("N) Next Page    P) Previous Page    F) First Page    L) Last Page    G) Goto");
        System.out.println("B) Write        R) Read (id)        U) Update        D) Delete       S) Search (name)");
        System.out.println("Se) Set rows    Sa) Save            Un) Unsaved      Ba) Backup      Re) Restore");
        System.out.println("E) Exit");
        System.out.print("=> Choose an option: ");
    }

    // Get user's menu choice
    public String getUserChoice() {
        String choice = scanner.nextLine().trim().toUpperCase();
        return choice.isEmpty() ? "" : choice; // Return empty string if no input, otherwise uppercase choice
    }

    public int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear invalid input
            System.out.print(prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return value;
    }

    public String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Clear invalid input
            System.out.print(prompt);
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        return value;
    }
}
