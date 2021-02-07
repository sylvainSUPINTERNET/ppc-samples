package com.ppcsamples.apisampler.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class FiltersConfig {

    @Autowired
    AuthFilter authFilter;
    @Bean
    public FilterRegistrationBean FilterRegistration() {
        FilterRegistrationBean registration = new  FilterRegistrationBean();
        registration.setFilter(authFilter);
        registration.addUrlPatterns("/api/v1/samples*");
        return registration;
    }
}
