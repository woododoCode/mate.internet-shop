package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

@WebServlet("/registration")
public class UserRegistrationController extends HttpServlet {
    private static final Injector INJECTOR =
            Injector.getInstance("mate.academy.internetshop");
    private UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);
    private ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("pwd");
        String passwordConfirm = req.getParameter("pwd-confirm");
        System.out.println(
                "name: " + name + "\n"
                + "login: " + login + "\n"
                + "pwd: " + password + "\n"
                + "pwdc: " + passwordConfirm
        );

        if (password.equals(passwordConfirm)
                && (!name.isBlank() && !login.isBlank())) {
            resp.sendRedirect(req.getContextPath() + "/");
            User user = new User(name, login, password);
            userService.create(user);
            ShoppingCart cart = new ShoppingCart(user);
            shoppingCartService.create(cart);
        } else if (!password.equals(passwordConfirm)) {
            req.setAttribute("message", "Your passwords don't match");
            req.setAttribute("login", login);
            req.setAttribute("name", name);
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        } else {
            req.setAttribute("message2", "Name or Login can't be empty");
            req.setAttribute("login", login);
            req.setAttribute("name", name);
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        }
    }
}
