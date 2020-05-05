package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

@WebServlet("/injectData")
public class InjectDataController extends HttpServlet {
    private static final Injector INJECTOR =
            Injector.getInstance("mate.academy.internetshop");
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);
    private final ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User bilbo = new User("Bilbo","bilbo", "bilbo");
        bilbo.setRoles(Set.of(Role.of("USER")));
        User frodo = new User("Frodo", "frodo", "frodo");
        frodo.setRoles(Set.of(Role.of("USER")));
        User admin = new User("admin", "admin", "admin");
        admin.setRoles(Set.of(Role.of("ADMIN")));
        userService.create(bilbo);
        userService.create(frodo);
        userService.create(admin);
        Product p1 = new Product("vata", 15, 987.55);
        Product p3 = new Product("hata", 1, 78.95);
        Product p2 = new Product("nata", 2, 11.36);
        productService.create(p1);
        productService.create(p2);
        productService.create(p3);
        ShoppingCart cart = new ShoppingCart(bilbo);
        shoppingCartService.create(cart);
        shoppingCartService.addProduct(cart, p1);
        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}
