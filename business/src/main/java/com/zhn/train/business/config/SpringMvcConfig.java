package com.zhn.train.business.config;


import com.zhn.train.common.interceptor.LogInterceptor;
import com.zhn.train.common.interceptor.MemberInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration("BusinessConfiguration")
public class SpringMvcConfig implements WebMvcConfigurer {

   @Resource
   LogInterceptor logInterceptor;
   @Resource
   MemberInterceptor memberInterceptor;

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(logInterceptor)
               .addPathPatterns("/**");
       registry.addInterceptor(memberInterceptor)
              .addPathPatterns("/**")
              .excludePathPatterns(
                      "/business/hello"
              );
   }
}
