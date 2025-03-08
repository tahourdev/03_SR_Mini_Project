package com.productmanagement;

import com.productmanagement.controller.ProductController;

public class Main {
    public static void main(String[] args) {
        ProductController controller = new ProductController();
        controller.showAll(controller);
    }
}


//origin/design-console-ui
