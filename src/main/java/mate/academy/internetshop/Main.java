package mate.academy.internetshop;

import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.ProductService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        productService.create(new Product("two noodles", 19.95));
        productService.create(new Product("three noodles", 29.95));
        productService.create(new Product("whole set of noodles", 35.90));

        productService.getAll().forEach(it -> System.out.println(it.toString()));

        Product product1 = productService.get(2L);
        System.out.println(product1.toString());

        productService.delete(2L);
        productService.update(productService.get(3L));

        productService.getAll().forEach(it -> System.out.println(it.toString()));

    }
}
