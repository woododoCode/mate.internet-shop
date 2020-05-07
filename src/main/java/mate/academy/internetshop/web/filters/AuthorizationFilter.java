package mate.academy.internetshop.web.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class AuthorizationFilter implements Filter {
    private static final Injector INJECTOR =
            Injector.getInstance("mate.academy.internetshop");
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);
    private Map<String, Set<Role.RoleName>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String[] adminUrls = filterConfig.getInitParameter("Admin urls").split(",");
        for (String url: adminUrls) {
            protectedUrls.put(url, Set.of(Role.RoleName.ADMIN));
        }
        String[] userUrls = filterConfig.getInitParameter("User urls").split(",");
        for (String url: userUrls) {
            protectedUrls.put(url, Set.of(Role.RoleName.USER));
        }
    }

    @Override
    public void doFilter(ServletRequest request,ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String url = req.getServletPath();
        if (protectedUrls.get(url) == null) {
            chain.doFilter(req, resp);
            return;
        }
        Long userId = (Long) req.getSession().getAttribute("user_id");
        User user = userService.get(userId);
        if (isAuthorized(user, protectedUrls.get(url))) {
            chain.doFilter(req, resp);
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/errors/403.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {

    }

    private boolean isAuthorized(User user, Set<Role.RoleName> authorizedRoles) {
        for (Role.RoleName authorizedRole: authorizedRoles) {
            for (Role userRole: user.getRoles()) {
                if (authorizedRole.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
