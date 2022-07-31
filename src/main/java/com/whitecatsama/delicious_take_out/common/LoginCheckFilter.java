package com.whitecatsama.delicious_take_out.common;

import com.alibaba.fastjson.JSON;
import com.whitecatsama.delicious_take_out.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@Component
@WebFilter(filterName = "logincheckfilter",urlPatterns = "/")
public class LoginCheckFilter implements Filter {
    private static final AntPathMatcher PATH_MATCHER= new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String requestUri = request.getRequestURI();
        String urls[] = new String[]{
                "/employee/login","/employee/logout","/backend/**","/fornt/**"
        };
        boolean check = check(urls,requestUri);
        if(check){
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("employee")!=null){
            filterChain.doFilter(request,response);
            return;
        }
        response.getWriter().write(JSON.toJSONString(new Result(0,"NOTLOGIN")));
        return;
    }
    public boolean check(String[]urls,String requestUri){
        for (String url : urls) {
           boolean flag = PATH_MATCHER.match(url,requestUri);
           if(flag){
               return true;
           }
        }
        return false;

    }
}
