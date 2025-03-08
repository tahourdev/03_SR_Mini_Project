package com.productmanagement.view;

import com.productmanagement.controller.ProductController;
import com.productmanagement.model.Product;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductView {
    private int pageSize = 2;
    private List<Product> writeProducts;

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
                    System.out.println("ID: " + controller.getId());
                    while (true){
                        System.out.print("Enter Product Name : ");
                        String name = Utility.scanner.nextLine();
                        if(name.trim().isEmpty()){
                            System.out.println(Utility.X_MARK + Utility.RED + " Empty space is not allow here!!!" + Utility.RESET_TEXT_COLOUR);
                        }else {
                            break;
                        }
                    }
                    double price = Double.parseDouble(Utility.validation("^\\s*\\d*$", "Enter Price : ", "Only positive number is accepted!!!"));
                }
                case "r" ->{
                    String getId = Utility.validation("^\\s*\\d+$", "Please input id to get record : ", "Only positive number is accepted!!!");
                    controller.showProductById(Integer.parseInt(getId));
                }
                case "u" ->{}
                case "d" ->{}
                case "s" ->{}
                case "se" ->{}
                case "sa" ->{}
                case "un" ->{}
                case "ba" ->{
                    String backupResult = controller.backUpFile();
                    System.out.println(backupResult);
                }
                case "re" ->{
                    System.out.println("\nList of Data backup : ");
                    String[] backups = controller.listBackups();
                    for (String backup : backups) {
                        System.out.println(backup);
                    }
                    System.out.print("=> Enter backup id to restore : ");
                    int backupId = Integer.parseInt(Utility.scanner.nextLine());
                    controller.restoreDatabase(backupId);
                }
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
}
