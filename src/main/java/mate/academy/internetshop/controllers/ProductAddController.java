package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.ProductService;

@WebServlet("/newprod")
public class ProductAddController extends HttpServlet {
    private static final Injector INJECTOR =
            Injector.getInstance("mate.academy.internetshop");
    private ProductService productService = (
            ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/products/newprod.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String amountS = req.getParameter("amount");
        String priceS = req.getParameter("price");
        int amount = Integer.parseInt(amountS);
        Double price = Double.valueOf(priceS);
        if (name.isEmpty()) {
            req.setAttribute("message", "Name of product can't be empty");
            req.getRequestDispatcher("/WEB-INF/views/products/").forward(req, resp);
        } else if (amount < 0) {
            req.setAttribute("message2", "Amount can't be lower then 0");
            req.getRequestDispatcher("/WEB-INF/views/products/newprod.jsp").forward(req, resp);
        } else if (priceS.length() <= 0) {
            req.setAttribute("message3", "Price must be higher then 0");
            req.getRequestDispatcher("/WEB-INF/views/products/newprod.jsp").forward(req, resp);
        } else {
            productService.create(new Product(name, amount, price));
            req.getRequestDispatcher("/WEB-INF/views/products/newprod.jsp").forward(req, resp);
            req.setAttribute("message4", "Product added successfully");

        }
        System.out.println(
                "name: " + name + "\n"
                        + "price: " + price + "\n"
                        + "amount: " + amount
        );
    }
}
