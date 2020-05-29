package dev.internetshop.controllers.order;

import dev.internetshop.lib.Injector;
import dev.internetshop.model.Product;
import dev.internetshop.model.ShoppingCart;
import dev.internetshop.service.OrderService;
import dev.internetshop.service.ShoppingCartService;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cart/order/complete")
public class OrderCreateController extends HttpServlet {
    private static final Injector INJECTOR =
            Injector.getInstance("dev.internetshop");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long userId = (Long) req.getSession().getAttribute("user_id");
        ShoppingCart cart = shoppingCartService.getByUserId(userId);
        List<Product> products = List.copyOf(cart.getProducts());
        shoppingCartService.clear(cart);
        orderService.completeOrder(products, userId);
        resp.sendRedirect(req.getContextPath() + "/user/orders");
    }
}
