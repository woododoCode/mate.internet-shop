package dev.internetshop.controllers.order;

import dev.internetshop.lib.Injector;
import dev.internetshop.model.Order;
import dev.internetshop.service.OrderService;
import dev.internetshop.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/orders")
public class OrderViewController extends HttpServlet {
    private static final Injector INJECTOR =
            Injector.getInstance("dev.internetshop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("user_id");
        String userName = userService.get(userId).getName();
        List<Order> userOrders = orderService.getUserOrders(userService.get(userId));
        req.setAttribute("userOrders", userOrders);
        req.setAttribute("userName", userName);
        req.getRequestDispatcher("/WEB-INF/views/orders/orders.jsp").forward(req, resp);
    }
}
