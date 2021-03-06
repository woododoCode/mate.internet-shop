package dev.internetshop.web.filters;

import dev.internetshop.lib.Injector;
import dev.internetshop.service.UserService;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR =
            Injector.getInstance("dev.internetshop");
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String url = req.getServletPath();
        if (url.equals("/login") || url.equals("/injectData") || url.equals("/registration")) {
            filterChain.doFilter(req, resp);
            return;
        }
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        String userName = (String) req.getSession().getAttribute("user_name");
        if (userId == null || userService.get(userId) == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        filterChain.doFilter(req, resp);
        req.setAttribute("user_name", userName);
        req.setAttribute("user_id", userId);
    }

    @Override
    public void destroy() {
    }
}
