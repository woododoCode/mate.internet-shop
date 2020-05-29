package dev.internetshop.controllers.product;

import dev.internetshop.lib.Injector;
import dev.internetshop.model.Product;
import dev.internetshop.service.ProductService;
import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/product/add")
public class ProductAddController extends HttpServlet {
    private static final Injector INJECTOR =
            Injector.getInstance("dev.internetshop");
    private final ProductService productService = (
            ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/admin/addproduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String priceS = req.getParameter("price");
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceS));
        productService.create(new Product(name, price));
        req.getRequestDispatcher("/WEB-INF/views/admin/addproduct.jsp").forward(req, resp);
    }
}
