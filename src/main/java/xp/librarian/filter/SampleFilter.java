package xp.librarian.filter;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

/**
 * @author xp
 */
@WebFilter
public class SampleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
