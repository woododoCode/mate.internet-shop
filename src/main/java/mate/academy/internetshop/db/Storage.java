package mate.academy.internetshop.db;

import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;

public class Storage {
    public static final List<Product> products = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    public static final List<ShoppingCart> shoppingCarts = new ArrayList<>();

    private static Long productId = 0L;
    private static Long shoppingCartId = 0L;
    private static Long userId = 0L;
    private static Long orderId = 0L;

    public static Product addProduct(Product product) {
        productId++;
        product.setId(productId);
        products.add(product);
        return product;
    }

    public static ShoppingCart addCart(ShoppingCart shoppingCart) {
        shoppingCartId++;
        shoppingCart.setId(shoppingCartId);
        shoppingCarts.add(shoppingCart);
        return shoppingCart;
    }

    public static User addUser(User user) {
        userId++;
        user.setUserId(userId);
        users.add(user);
        return user;
    }

    public static Order addOrder(Order order) {
        userId++;
        order.setOrderID(orderId);
        orders.add(order);
        return order;
    }

}
