package com.motta.salary_service.config;



import com.motta.salary_service.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

//@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean filter= new FilterRegistrationBean();
        filter.setFilter(new JwtFilter());

//        provide endpoints which needs to be restricted.
//        All Endpoints would be restricted if unspecified
        filter.addUrlPatterns("*");
        return filter;
    }
}