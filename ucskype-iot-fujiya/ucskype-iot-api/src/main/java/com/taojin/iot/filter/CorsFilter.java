package com.taojin.iot.filter;


import org.springframework.stereotype.Component;  
  
import javax.servlet.*;  
import javax.servlet.http.HttpServletResponse;  
import java.io.IOException;  
  
/** 
 *  
 *  跨域过滤器 
 * @author wangjie 
 * @version  
 * @since 2016年6月19日 
 */  
@Component  
public class CorsFilter implements Filter {  
  
    final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CorsFilter.class);  
  
  
  
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;  
        response.setHeader("Access-Control-Allow-Origin", "*");  
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
        response.setHeader("Access-Control-Max-Age", "3600");  
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,authorkey,content-type");
        chain.doFilter(req, res);  
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}  

