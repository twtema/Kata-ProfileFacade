package org.kata.config;

import org.kata.servlet.LoggableDispatcherServlet;
import org.kata.servlet.filter.LoggingFilterChain;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class ProfileServiceConfig {

    @Bean
    public ServletRegistrationBean dispatcherRegistration() {
        return new ServletRegistrationBean(dispatcherServlet());
    }

    @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet() {
        return new LoggableDispatcherServlet(loggingFilterChain());
    }

    @Bean
    public LoggingFilterChain loggingFilterChain() {
        return new LoggingFilterChain();
    }
}
