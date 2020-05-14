package mate.academy.internetshop.controllers.user;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

@WebServlet("/registration")
public class UserRegistrationController extends HttpServlet {
    private static final Injector INJECTOR =
            Injector.getInstance("mate.academy.internetshop");
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
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
        if (password.equals(passwordConfirm)) {
            User user = new User(name, login, password);
            user.setRoles(Set.of(Role.of("USER")));
            userService.create(user);
            ShoppingCart cart = new ShoppingCart(user.getId());
            shoppingCartService.create(cart);
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("message", "Your passwords don't match");
            req.setAttribute("login", login);
            req.setAttribute("name", name);
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        }
    }
}
