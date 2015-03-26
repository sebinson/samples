package net.sebinson.common.cache.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractSessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        doFilterInternal((HttpServletRequest) request, (HttpServletResponse) response);
        chain.doFilter(request, response);

    }

    protected abstract void doFilterInternal(HttpServletRequest request, HttpServletResponse response);

    @Override
    public void destroy() {

    }
}
