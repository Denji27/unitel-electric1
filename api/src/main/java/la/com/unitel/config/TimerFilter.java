package la.com.unitel.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter("/*")
public class TimerFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(TimerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("TimerFilter int().");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        long time = System.currentTimeMillis();
        try {
            chain.doFilter(req, resp);
        } finally {
            time = System.currentTimeMillis() - time;
            logger.info("PROCESSED "+((HttpServletRequest) req).getMethod()+":{} in [{}] ms ", ((HttpServletRequest) req).getRequestURI(),  time);
        }
    }

    @Override
    public void destroy() {
        logger.info("TimerFilter destroy().");
    }
}
