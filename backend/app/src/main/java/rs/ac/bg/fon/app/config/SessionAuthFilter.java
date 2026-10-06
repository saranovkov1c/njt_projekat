package rs.ac.bg.fon.app.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class SessionAuthFilter extends OncePerRequestFilter {

    // Prefiksi ruta koje sme da vidi SAMO ulogovan ADMIN (User.role == ADMIN)
    private static final String[] ADMIN_ONLY_PREFIXES = {
        "/categories/add", "/categories/update", "/categories/delete",
        "/products/add", "/products/update", "/products/delete",
        "/users/add", "/users/update", "/users/delete", "/users/all", "/users/getUser",
        "/clients/all", "/clients/delete", "/clients/update",
        "/orders/all", "/orders/delete", "/orders/update",
        "/order-items/delete", "/order-items/update"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

       
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean isProtected = false;
        for (String prefix : ADMIN_ONLY_PREFIXES) {
            if (path.startsWith(prefix)) {
                isProtected = true;
                break;
            }
        }

        if (!isProtected) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false); // false = ne pravi novu sesiju
        Object role = (session != null) ? session.getAttribute("role") : null;

        if (session == null || !"ADMIN".equals(role)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Niste ulogovani kao administrator.\"}");
            return; 
        }

        filterChain.doFilter(request, response);
    }
}
