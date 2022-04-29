package com.stas.parceldelivery.publcapi.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@Component
@WebFilter(filterName = "requestCountingFilter")
public class RequestIdentifyingFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger("http");
    private AtomicLong requestCounter = new AtomicLong(0);
    
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long requestId = requestCounter.incrementAndGet();
        request.setAttribute("id", requestId);
        Thread.currentThread().setName("http." + requestId);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
