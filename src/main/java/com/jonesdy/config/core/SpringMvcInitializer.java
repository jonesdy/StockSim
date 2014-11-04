package com.jonesdy.config.core;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.jonesdy.config.AppConfig;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
   @Override
   protected Class<?>[] getRootConfigClasses()
   {
      return new Class[] {AppConfig.class};
   }
   
   @Override
   protected Class<?>[] getServletConfigClasses()
   {
      return new Class[] {AppConfig.class};
   }
   
   @Override
   protected String[] getServletMappings()
   {
      return new String[] {"/"};
   }
}
