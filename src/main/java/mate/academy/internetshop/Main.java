package mate.academy.internetshop;

import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        final ProductService productService =
                (ProductService) injector.getInstance(ProductService.class);
        final UserService userService =
                (UserService) injector.getInstance(UserService.class);
        final ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        final OrderService orderService =
                (OrderService) injector.getInstance(OrderService.class);
        Product product1 = new Product("coffee", 250.00);
        Product product2 = new Product("noodles", 1500.00);
        Product product3 = new Product("traktor", 1000.00);

        productService.create(product1);
        productService.create(product2);
        productService.create(product3);

        System.out.println(productService.get(product2.getId()));
        System.out.println(productService.getAll());

        Product product4 = new Product("lada", 20.00);
        System.out.println(productService.delete(product4.getId()));

        product3.setName("Belorus");
        productService.update(product3);
        System.out.println(productService.getAll());
        User user1 = new User("Bilbo", "my_treasure", "123123123");
        User user2 = new User("King", "marksman", "987654321");
        userService.create(user1);
        userService.create(user2);
        System.out.println(userService.get(user1.getId()));
        System.out.println(userService.getAll());

        user1.setLogin("Frodo");
        userService.update(user1);
        System.out.println(userService.get(user1.getId()));

        userService.delete(user2.getId());
        System.out.println(userService.getAll());

        ShoppingCart cart1 = shoppingCartService.getByUserId(user1.getId());
        shoppingCartService.addProduct(cart1, product3);
        shoppingCartService.addProduct(cart1, product1);
        shoppingCartService.addProduct(cart1, product2);
        System.out.println(shoppingCartService.getAllProducts(cart1));

        shoppingCartService.deleteProduct(cart1, product2);
        System.out.println(shoppingCartService.getByUserId(user1.getId()));

        Order order1 = orderService.completeOrder(
                shoppingCartService.getAllProducts(cart1), user1);
        System.out.println(shoppingCartService.getByUserId(user1.getId()));

        System.out.println(orderService.get(order1.getId()));
        System.out.println(orderService.getUserOrders(user1));

        userService.create(user2);
        productService.create(product4);
        ShoppingCart peterCart = shoppingCartService.getByUserId(user2.getId());
        shoppingCartService.addProduct(peterCart, product4);
        Order peterOrder = orderService
                .completeOrder(shoppingCartService.getAllProducts(peterCart), user2);

        System.out.println(orderService.getAll());
        orderService.delete(peterOrder.getId());
        System.out.println(orderService.getAll());

    }
}
