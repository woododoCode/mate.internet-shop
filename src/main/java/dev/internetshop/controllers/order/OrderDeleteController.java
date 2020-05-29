package dev.internetshop.controllers.order;

import dev.internetshop.lib.Injector;
import dev.internetshop.service.OrderService;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/order/delete")
public class OrderDeleteController extends HttpServlet {
    private static final Injector INJECTOR =
            Injector.getInstance("dev.internetshop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String idString = req.getParameter("id");
        Long id = Long.valueOf(idString);
        orderService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/user/orders");
    }
}
