package com.reji.www.filter;


import com.alibaba.fastjson.JSON;
import com.reji.www.common.BaseContext;
import com.reji.www.common.Result;
import com.reji.www.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@WebFilter(filterName = "LoginFilter",urlPatterns = "/*")
public class LoginFilter implements Filter {

    static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;



        String requestURI = request.getRequestURI();

        String[] urls = {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/loginout",
                "/user/login",
                "/user/sendMsg"
        };
        if (check(urls,requestURI)){
            filterChain.doFilter(request,response);
            return;
        }

        if (request.getSession().getAttribute("user")!=null){

            Employee employee = (Employee) request.getSession().getAttribute("user");

            BaseContext.setCurrentId(employee.getId());

            filterChain.doFilter(request,response);
            return;
        }

        System.out.println(request.getSession().getAttribute("userFrontId"));

        if (request.getSession().getAttribute("userFrontId")!=null){

            Long id = (Long) request.getSession().getAttribute("userFrontId");

            BaseContext.setCurrentId(id);

            filterChain.doFilter(request,response);
            return;
        }

//        response.getWriter().write(JSON.toJSONString(Result.error("UN_LOGIN")));
        response.setHeader("Content-Type", "text/ plain;charset=utf-8");
        response.getWriter().println(JSON.toJSONString(Result.error("未登录")));
        log.info("拦截请求：{}",requestURI);
        return;


    }

    public boolean check(String[] urls, String urlRequest){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, urlRequest);
            if (match){
                return true;
            }
        }
        return false;
    }
}
