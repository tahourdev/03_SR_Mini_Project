package com.productmanagement.view;

import com.productmanagement.model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.Scanner;

public class Utility {
    public static final String RED = "\u001b[31m";
    public static final String GREEN = "\u001b[32m";
    public static final String YELLOW = "\u001b[33m";
    public static final String BLUE = "\u001b[34m";
    public static final String RESET_TEXT_COLOUR = "\u001b[0m";
    public static final String X_MARK = "❌";
    public static final String CHECK_MARK = "✅";
    public static final String WARNING_SIGHT = "⚠️";
    public static final Scanner scanner = new Scanner(System.in);

    public static Table getTableHeader() {
        Table table = new Table(5, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        CellStyle style = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        table.setColumnWidth(0, 13, 100);
        table.setColumnWidth(1, 30, 100);
        table.setColumnWidth(2, 16, 100);
        table.setColumnWidth(3, 10, 100);
        table.setColumnWidth(4, 16, 100);
        table.addCell(Utility.BLUE + "ID" + Utility.RESET_TEXT_COLOUR, style);
        table.addCell(Utility.BLUE + "Name" + Utility.RESET_TEXT_COLOUR, style);
        table.addCell(Utility.BLUE + "Unit Price" + Utility.RESET_TEXT_COLOUR, style);
        table.addCell(Utility.BLUE + "Qty" + Utility.RESET_TEXT_COLOUR, style);
        table.addCell(Utility.BLUE + "Imported Date" + Utility.RESET_TEXT_COLOUR, style);
        return table;
    }
    public static void optionMenu(){
        System.out.println("                                 ----------- Menu -----------");
        System.out.println(Utility.GREEN + "\tN. " + Utility.RESET_TEXT_COLOUR + "Next Page\t\t" +
                Utility.GREEN + "P. " + Utility.RESET_TEXT_COLOUR + "Previous Page\t\t" +
                Utility.GREEN + "F. " + Utility.RESET_TEXT_COLOUR + "First Page\t\t" +
                Utility.GREEN + "L. " + Utility.RESET_TEXT_COLOUR + "Last Page\t\t" +
                Utility.GREEN + "G. " + Utility.RESET_TEXT_COLOUR + "Goto");
        System.out.println("\n" +
                Utility.GREEN + "W) " + Utility.RESET_TEXT_COLOUR + "Write\t\t" +
                Utility.GREEN + "R) " + Utility.RESET_TEXT_COLOUR + "Read (id)\t" +
                Utility.GREEN + "U) " + Utility.RESET_TEXT_COLOUR + "Update\t\t" +
                Utility.GREEN + "D) " + Utility.RESET_TEXT_COLOUR + "Delete\t\t" +
                Utility.GREEN + "S) " + Utility.RESET_TEXT_COLOUR + "Search (name)\t\t" +
                Utility.GREEN + "Se) " + Utility.RESET_TEXT_COLOUR + "Set rows\n" +
                Utility.GREEN + "Sa) " + Utility.RESET_TEXT_COLOUR + "Save\t\t" +
                Utility.GREEN + "Un) " + Utility.RESET_TEXT_COLOUR + "Unsaved\t\t" +
                Utility.GREEN + "Ba) " + Utility.RESET_TEXT_COLOUR + "Backup\t\t" +
                Utility.GREEN + "Re) " + Utility.RESET_TEXT_COLOUR + "Restore\t\t" +
                Utility.GREEN + "E) " + Utility.RESET_TEXT_COLOUR + "Exit\n");
    }
    public static String validation(String regex, String inputMessage, String errorMessage){
        Scanner sc = new Scanner(System.in);
        String string;
        while (true){
            System.out.print(inputMessage);
            string = sc.nextLine();
            if(string.trim().isEmpty()){
                System.out.println(RED + X_MARK + " Space is not allow here!!!" + RESET_TEXT_COLOUR + "\n");
                continue;
            }
            if (string.matches(regex)){
                return string;
            }else {
                System.out.println(RED + X_MARK + " Invalid format. " + errorMessage + RESET_TEXT_COLOUR + "\n");
            }
        }
    }
    public static String validateGoto(int totalPage){
        Scanner sc = new Scanner(System.in);
        String string;
        while (true){
            System.out.print("Enter page number: ");
            string = sc.nextLine();
            if(string.trim().isEmpty()){
                System.out.println(RED + X_MARK + " Space is not allow here!!!" + RESET_TEXT_COLOUR + "\n");
                continue;
            }
            if (string.matches("^\\d+$")){
                if(Integer.parseInt(string) == 0 || Integer.parseInt(string) > totalPage){
                    System.out.println(RED + X_MARK + " Total page is " + totalPage + ", you can't enter number 0 or more than total pages!!!" + RESET_TEXT_COLOUR + "\n");
                    continue;
                }
                return string;
            }else {
                System.out.println(RED + X_MARK + " Invalid format. Accepted only a positive number!!!" + RESET_TEXT_COLOUR + "\n");
            }
        }
    }

    public static void addCellIntoTable(Product product, Table table, CellStyle style) {
        table.addCell(Utility.GREEN + product.getId() + Utility.RESET_TEXT_COLOUR, style);
        table.addCell(product.getName(), style);
        table.addCell("$" + product.getUnitPrice(), style);
        table.addCell(String.valueOf(product.getQuantity()), style);
        table.addCell(product.getImportedDate().toString(), style);
    }

    public static void isNullObject (){
        Table table = getTableHeader();
        CellStyle style = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        for(int i = 0; i < 5; i++) {
            table.addCell("---", style);
        }
        System.out.println(table.render());
        System.out.print(YELLOW + "Press Enter to Continue..." + RESET_TEXT_COLOUR);
        scanner.nextLine();
    }
}
