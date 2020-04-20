package com.whuamps.config;

//import com.whuamps.component.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    //页面重定向
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/login").setViewName("login");
                registry.addViewController("/hello").setViewName("helloworld");
            }
//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
//                //Springboot已经做好了静态资源映射
//                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/index.html","/login","/webjars/**","/asserts/**");
//            }

        };
    }
//    //把拦截器添加到容器中
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //把非正常的请求拦截
//        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/index.html","/","/my_login","/login","/webjars/**","/asserts/**");
//    }
}
