package com.productmanagement.view;

import com.productmanagement.controller.ProductController;
import com.productmanagement.model.Product;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductView {
    private int pageSize = 4;
    private int totalPages = 0;

    private List<Product> writeProductsList;

    public void displayAllProducts(List<Product> products, ProductController controller) {
        int currentPage = 1;
        int totalPages = (int) Math.ceil((double) products.size() / pageSize);
        while (true) {
            displayMenuPage(products, currentPage, totalPages);
            Utility.optionMenu();

            String choice = Utility.validation("^(?:[\\s*NnPpFfLlGgWwRrUuDdSsEe]|se|Se|SE|sE|sa|SA|Sa|sA|un|UN|Un|uN|ba|BA|Ba|bA|re|RE|Re|rE)$", Utility.YELLOW + "=> Choose an option() : " + Utility.RESET_TEXT_COLOUR, "Allows only the menu Options!!!").toLowerCase();

            switch (choice) {
                case "f" -> currentPage = 1;
                case "n" -> {
                    if (currentPage < totalPages) currentPage++;
                }
                case "p" -> {
                    if (currentPage > 1) currentPage--;
                }
                case "l" -> currentPage = totalPages;
                case "g" -> {
                    int gotoPage = Integer.parseInt(Utility.validateGoto(totalPages));
                    if (gotoPage >= 1 && gotoPage <= totalPages) {
                        currentPage = gotoPage;
                    } else {
                        System.out.println(Utility.RED + "Page number out of range!" + Utility.RESET_TEXT_COLOUR);
                    }
                }
                case "e" -> {
                    System.out.println("\nExiting pagination...");
                    return;
                }
                case "w" ->{
                    int id;
                    if(writeProductsList.isEmpty()){
                        id = controller.getId();
                    }else {
                        id = writeProductsList.getLast().getId() + 1;
                    }
                    System.out.println("ID: " + id);
                    String name;
                    while (true){
                        System.out.print("Enter Product Name : ");
                        name = Utility.scanner.nextLine();
                        if(name.trim().isEmpty()){
                            System.out.println(Utility.X_MARK + Utility.RED + " Empty space is not allow here!!!" + Utility.RESET_TEXT_COLOUR);
                        }else {
                            break;
                        }
                    }
                    double price = Double.parseDouble(Utility.validation("^\\s*\\d*$", "Enter Price : ", "Only positive number is accepted!!!"));
                    int quantity = Integer.parseInt(Utility.validation("^\\s*\\d*$", "Enter Quantity : ", "Only positive number is accepted!!!"));
                    writeProductsList.add(new Product(id, name, price, quantity, LocalDate.now()));
                    System.out.print(Utility.YELLOW + "Press Enter to Continue..." + Utility.RESET_TEXT_COLOUR);
                    Utility.scanner.nextLine();
                }
                case "r" ->{
                    String getId = Utility.validation("^\\s*\\d+$", "Please input id to get record : ", "Only positive number is accepted!!!");
                    controller.showProductById(Integer.parseInt(getId));
                }
                case "u" ->{}
                case "d" ->{}
                case "s" ->{}
                case "se" ->{
                    System.out.print("Please input number row per page: ");
                    pageSize = Integer.parseInt(Utility.scanner.nextLine());
                    totalPages = (int) Math.ceil((double) products.size() / pageSize);
                }
                case "sa" ->{
                    System.out.println(Utility.GREEN + "'si' " + Utility.RESET_TEXT_COLOUR + "for saving insert products and " + Utility.GREEN + "'su' " + Utility.RESET_TEXT_COLOUR + "for saving update products or " + Utility.RED + "'b' " + Utility.RESET_TEXT_COLOUR + "for back to menu.");
                    String option = Utility.validation("^\\s*|si|SI|Si|sI|su|SU|Su|sU|B|b$", "Enter your option : ", "Only those in the menu!!!").toLowerCase();
                    switch (option){
                        case "si" ->{
                            if(writeProductsList.isEmpty()){
                                System.out.println(Utility.YELLOW + Utility.WARNING_SIGHT + " There is no data to save here!!!" + Utility.RESET_TEXT_COLOUR);
                                System.out.print(Utility.YELLOW + "Press Enter to Continue..." + Utility.RESET_TEXT_COLOUR);
                                Utility.scanner.nextLine();
                            }else {

                            }
                        }
                        case "su" ->{

                        }
                        case "b" ->{

                        }
                    }
                }
                case "un" ->{
                    System.out.println(Utility.GREEN + "'ui' " + Utility.RESET_TEXT_COLOUR + "for saving insert products and " + Utility.GREEN + "'uu' " + Utility.RESET_TEXT_COLOUR + "for saving update products or " + Utility.RED + "'b' " + Utility.RESET_TEXT_COLOUR + "for back to menu.");
                    String option = Utility.validation("^\\s*|ui|UI|Ui|uI|uu|UU|Uu|uU|B|b$", "Enter your option : ", "Only those in the menu!!!").toLowerCase();
                    switch (option){
                        case "ui" ->{
                            if(writeProductsList.isEmpty()){
                                Utility.isNullObject();
                            }else {
                                displayListProducts(writeProductsList);
                            }
                        }
                        case "uu" ->{

                        }
                        case "b" ->{

                        }
                    }
                }
                case "ba" ->{}
                case "re" ->{}
            }
        }
    }
    private void displayMenuPage(List<Product> products, int currentPage, int totalPages) {
        Table table = Utility.getTableHeader();
        CellStyle style = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        AtomicInteger counter = new AtomicInteger();
        List<Product> paginatedProducts = products.stream()
                .skip((long) (currentPage - 1) * pageSize)
                .limit(pageSize)
                .toList();
        System.out.println("\n");
        paginatedProducts.forEach(product -> {
            Utility.addCellIntoTable(product, table, style);
            counter.getAndIncrement();
        });
        table.addCell("Page : " + Utility.YELLOW + currentPage + Utility.RESET_TEXT_COLOUR +
                " of " + Utility.RED + totalPages + Utility.RESET_TEXT_COLOUR, style, 2);
        table.addCell("Total Record : " + Utility.GREEN + products.size() + Utility.RESET_TEXT_COLOUR, style, 3);
        System.out.println(table.render());
    }

    public void displayPage(Product product) {
        Table table = Utility.getTableHeader();
        CellStyle style = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        Utility.addCellIntoTable(product, table, style);
        System.out.println(table.render());
        System.out.print(Utility.YELLOW + "Press Enter to Continue..." + Utility.RESET_TEXT_COLOUR);
        Utility.scanner.nextLine();
    }

    public void displayListProducts(List<Product> list) {
        Table table = Utility.getTableHeader();
        CellStyle style = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        list.forEach(product -> {
            Utility.addCellIntoTable(product, table, style);
        });
        System.out.println(table.render());
        System.out.print(Utility.YELLOW + "Press Enter to Continue..." + Utility.RESET_TEXT_COLOUR);
        Utility.scanner.nextLine();
    }
}
