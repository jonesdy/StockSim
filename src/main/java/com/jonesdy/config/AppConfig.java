package com.jonesdy.config;

import java.text.DateFormat;
import java.util.List;

import javax.sql.DataSource;

import nz.net.ultraq.thymeleaf.LayoutDialect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@EnableWebMvc
@Configuration
@ComponentScan({"com.jonesdy.web.*"})
@Import({SecurityConfig.class})
public class AppConfig extends WebMvcConfigurerAdapter
{
   @Bean(destroyMethod = "")
   public DataSource dataSource()
   {
      final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
      dsLookup.setResourceRef(true);
      return dsLookup.getDataSource("java:comp/env/jdbc/stocksimdb");
   }
   
   @Bean
   public ThymeleafViewResolver viewResolver()
   {
      ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
      viewResolver.setTemplateEngine(templateEngine());
      return viewResolver;
   }
   
   @Bean
   public SpringTemplateEngine templateEngine()
   {
      SpringTemplateEngine templateEngine = new SpringTemplateEngine();
      templateEngine.setTemplateResolver(templateResolver());
      templateEngine.addDialect(new LayoutDialect());
      templateEngine.addDialect(new SpringSecurityDialect());
      return templateEngine;
   }
   
   @Bean
   public ServletContextTemplateResolver templateResolver()
   {
      ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
      templateResolver.setPrefix("/WEB-INF/views/");
      templateResolver.setSuffix(".html");
      templateResolver.setTemplateMode("HTML5");
      templateResolver.setOrder(1);
      // Disable caching for now
      templateResolver.setCacheable(false);
      return templateResolver;
   }
   
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry)
   {
      registry.addResourceHandler("/js/**").addResourceLocations("/js/");
   }
   
   @Override
   public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
   {
      Gson gson = new GsonBuilder().setDateFormat(DateFormat.LONG).create();
      GsonHttpMessageConverter messageConverter = new GsonHttpMessageConverter();
      messageConverter.setGson(gson);
      converters.add(messageConverter);
   }
}